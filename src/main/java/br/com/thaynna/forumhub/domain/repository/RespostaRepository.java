package br.com.thaynna.forumhub.domain.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.thaynna.forumhub.domain.model.Resposta;
import br.com.thaynna.forumhub.domain.model.Topico;

public interface RespostaRepository extends JpaRepository<Resposta, Long> {
    List<Resposta> findByTopico(Topico topico);
}
