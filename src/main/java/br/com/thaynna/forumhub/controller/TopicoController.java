package br.com.thaynna.forumhub.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import br.com.thaynna.forumhub.domain.dto.AtualizacaoTopicoDTO;
import br.com.thaynna.forumhub.domain.dto.TopicoRequestDTO;
import br.com.thaynna.forumhub.domain.dto.TopicoResponseDTO;
import br.com.thaynna.forumhub.domain.model.Curso;
import br.com.thaynna.forumhub.domain.model.Topico;
import br.com.thaynna.forumhub.domain.model.Usuario;
import br.com.thaynna.forumhub.domain.repository.CursoRepository;
import br.com.thaynna.forumhub.domain.repository.TopicoRepository;
import br.com.thaynna.forumhub.domain.repository.UsuarioRepository;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/topicos")
public class TopicoController {

        @Autowired
        private TopicoRepository topicoRepository;

        @Autowired
        private UsuarioRepository usuarioRepository;

        @Autowired
        private CursoRepository cursoRepository;

    // -------------------- CREATE --------------------
        @PostMapping
        @Transactional
        public ResponseEntity<TopicoResponseDTO> criar(@RequestBody @Valid TopicoRequestDTO dto) {
                topicoRepository.findByTituloAndMensagem(dto.getTitulo(), dto.getMensagem())
                        .ifPresent(t -> { throw new RuntimeException("Tópico já existe!"); });

                Usuario autor = usuarioRepository.findById(dto.getAutorId())
                        .orElseThrow(() -> new RuntimeException("Autor não encontrado"));

                Curso curso = cursoRepository.findById(dto.getCursoId())
                        .orElseThrow(() -> new RuntimeException("Curso não encontrado"));

                Topico topico = new Topico();
                topico.setTitulo(dto.getTitulo());
                topico.setMensagem(dto.getMensagem());
                topico.setAutor(autor);
                topico.setCurso(curso);

                Topico salvo = topicoRepository.save(topico);

                return ResponseEntity.ok(new TopicoResponseDTO(
                        salvo.getTitulo(),
                        salvo.getMensagem(),
                        salvo.getDataCriacao(),
                        salvo.getEstado(),
                        salvo.getAutor().getNome(),
                        salvo.getCurso().getNome()
                ));
        }

    // -------------------- LIST ALL --------------------
        @GetMapping
        public ResponseEntity<List<TopicoResponseDTO>> listar() {
                List<TopicoResponseDTO> lista = topicoRepository.findAll().stream()
                        .map(t -> new TopicoResponseDTO(
                                t.getTitulo(),
                                t.getMensagem(),
                                t.getDataCriacao(),
                                t.getEstado(),
                                t.getAutor().getNome(),
                                t.getCurso().getNome()
                        ))
                        .toList();
                return ResponseEntity.ok(lista);
        }

    // -------------------- LIST RECENTS --------------------
        @GetMapping("/recentes")
        public ResponseEntity<List<TopicoResponseDTO>> listarRecentes() {
                Pageable pageable = PageRequest.of(0, 10, Sort.by("dataCriacao").ascending());
                List<TopicoResponseDTO> lista = topicoRepository.findAll(pageable).stream()
                        .map(t -> new TopicoResponseDTO(
                                t.getTitulo(),
                                t.getMensagem(),
                                t.getDataCriacao(),
                                t.getEstado(),
                                t.getAutor().getNome(),
                                t.getCurso().getNome()
                        ))
                        .toList();
                return ResponseEntity.ok(lista);
        }

    // -------------------- FILTER BY COURSE & YEAR --------------------
        @GetMapping("/buscar")
        public ResponseEntity<List<TopicoResponseDTO>> buscarPorCursoEAno(
                @RequestParam String curso,
                @RequestParam int ano) {
                List<TopicoResponseDTO> lista = topicoRepository.findByCursoAndAno(curso, ano).stream()
                        .map(t -> new TopicoResponseDTO(
                                t.getTitulo(),
                                t.getMensagem(),
                                t.getDataCriacao(),
                                t.getEstado(),
                                t.getAutor().getNome(),
                                t.getCurso().getNome()
                        ))
                        .toList();
                return ResponseEntity.ok(lista);
        }

    // -------------------- PAGINATION --------------------
        @GetMapping("/paginado")
        public ResponseEntity<Page<TopicoResponseDTO>> listarPaginado(
                @PageableDefault(size = 5, sort = "dataCriacao") Pageable pageable) {

        Page<Topico> pageTopico = topicoRepository.findAll(pageable);

        Page<TopicoResponseDTO> pageDto = pageTopico.map(topico -> 
                new TopicoResponseDTO(
                        topico.getTitulo(),
                        topico.getMensagem(),
                        topico.getDataCriacao(),
                        topico.getEstado(),
                        topico.getAutor().getNome(),
                        topico.getCurso().getNome()
                )
        );

        return ResponseEntity.ok(pageDto);
        }

    // -------------------- GET BY ID --------------------
        @GetMapping("/{id}")
        public ResponseEntity<TopicoResponseDTO> detalhar(@PathVariable Long id) {
        return topicoRepository.findById(id)
                .map(topico -> {
                        TopicoResponseDTO dto = new TopicoResponseDTO(
                                topico.getTitulo(),
                                topico.getMensagem(),
                                topico.getDataCriacao(),
                                topico.getEstado(),
                                topico.getAutor().getNome(),
                                topico.getCurso().getNome()
                        );
                        return ResponseEntity.ok(dto);
                })
                .orElse(ResponseEntity.notFound().build());
        }

    // -------------------- UPDATE --------------------
        @PutMapping("/{id}")
        @Transactional
        public ResponseEntity<TopicoResponseDTO> atualizar(
                @PathVariable Long id,
                @RequestBody @Valid AtualizacaoTopicoDTO dto) {

        var topicoOptional = topicoRepository.findById(id);

        if (topicoOptional.isEmpty()) {
                return ResponseEntity.notFound().build();
        }

        Topico topico = topicoOptional.get();

        boolean existeDuplicado = topicoRepository.existsByTituloAndMensagem(dto.titulo(), dto.mensagem());

        if (existeDuplicado &&
                (!topico.getTitulo().equals(dto.titulo()) || !topico.getMensagem().equals(dto.mensagem()))) {
                return ResponseEntity.badRequest().build();
        }

        // Atualiza os campos
        topico.setTitulo(dto.titulo());
        topico.setMensagem(dto.mensagem());
        topico.setEstado(dto.estado());

        TopicoResponseDTO dtoResponse = new TopicoResponseDTO(
                topico.getTitulo(),
                topico.getMensagem(),
                topico.getDataCriacao(),
                topico.getEstado(),
                topico.getAutor().getNome(),
                topico.getCurso().getNome()
        );

        return ResponseEntity.ok(dtoResponse);
        }

    // -------------------- DELETE --------------------
        @DeleteMapping("/{id}")
        @Transactional
        public ResponseEntity<Void> deletar(@PathVariable Long id) {
                return topicoRepository.findById(id)
                        .map(topico -> {
                        topicoRepository.delete(topico);
                        return ResponseEntity.noContent().<Void>build();
                        })
                        .orElse(ResponseEntity.notFound().build());
        }
}