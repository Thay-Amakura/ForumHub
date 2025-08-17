package br.com.thaynna.forumhub.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.thaynna.forumhub.domain.model.Curso;
import br.com.thaynna.forumhub.domain.repository.CursoRepository;
import jakarta.transaction.Transactional;

@RestController
@RequestMapping("/cursos")
public class CursoController {

    @Autowired
    private CursoRepository cursoRepository;

    @PostMapping
    @Transactional
    public Curso criar(@RequestBody Curso curso) {
        return cursoRepository.save(curso);
    }

    @GetMapping
    public List<Curso> listar() {
        return cursoRepository.findAll();
    }

    @GetMapping("/{id}")
    public Curso buscarPorId(@PathVariable Long id) {
        return cursoRepository.findById(id).orElseThrow();
    }

    @PutMapping("/{id}")
    @Transactional
    public Curso atualizar(@PathVariable Long id, @RequestBody Curso curso) {
        Curso existente = cursoRepository.findById(id).orElseThrow();
        existente.setNome(curso.getNome());
        existente.setCategoria(curso.getCategoria());
        return cursoRepository.save(existente);
    }

    @DeleteMapping("/{id}")
    @Transactional
    public void deletar(@PathVariable Long id) {
        cursoRepository.deleteById(id);
    }
}
