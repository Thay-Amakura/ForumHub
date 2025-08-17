package br.com.thaynna.forumhub.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.thaynna.forumhub.domain.model.Resposta;
import br.com.thaynna.forumhub.domain.model.Topico;
import br.com.thaynna.forumhub.domain.model.Usuario;
import br.com.thaynna.forumhub.domain.repository.RespostaRepository;
import br.com.thaynna.forumhub.domain.repository.TopicoRepository;
import br.com.thaynna.forumhub.domain.repository.UsuarioRepository;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/respostas")
public class RespostaController {

    @Autowired
    private RespostaRepository respostaRepository;

    @Autowired
    private TopicoRepository topicoRepository;

    @Autowired
    private UsuarioRepository usuarioRepository;

    @GetMapping
    public List<Resposta> listar() {
        return respostaRepository.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Resposta> detalhar(@PathVariable Long id) {
        return respostaRepository.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<Resposta> criar(@RequestBody @Valid Resposta dto) {
        Topico topico = topicoRepository.findById(dto.getTopico().getId())
                .orElseThrow(() -> new RuntimeException("Tópico não encontrado"));

        Usuario autor = usuarioRepository.findById(dto.getAutor().getId())
                .orElseThrow(() -> new RuntimeException("Usuário não encontrado"));

        dto.setTopico(topico);
        dto.setAutor(autor);

        return ResponseEntity.ok(respostaRepository.save(dto));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Resposta> atualizar(@PathVariable Long id, @RequestBody @Valid Resposta dto) {
        return respostaRepository.findById(id)
                .map(resposta -> {
                    resposta.setMensagem(dto.getMensagem());
                    return ResponseEntity.ok(respostaRepository.save(resposta));
                })
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(@PathVariable Long id) {
        return respostaRepository.findById(id)
                .map(resposta -> {
                    respostaRepository.delete(resposta);
                    return ResponseEntity.noContent().<Void>build();
                })
                .orElse(ResponseEntity.notFound().build());
    }
}
