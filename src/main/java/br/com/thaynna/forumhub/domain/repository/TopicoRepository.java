package br.com.thaynna.forumhub.domain.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import br.com.thaynna.forumhub.domain.model.Topico;

public interface TopicoRepository extends JpaRepository<Topico, Long> {
    Optional<Topico> findByTituloAndMensagem(String titulo, String mensagem);

    @Query("SELECT t FROM Topico t WHERE t.curso.nome = :curso AND YEAR(t.dataCriacao) = :ano")
    List<Topico> findByCursoAndAno(String curso, int ano);

    boolean existsByTituloAndMensagem(String titulo, String mensagem);
}
