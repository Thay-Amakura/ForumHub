package br.com.thaynna.forumhub.domain.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import br.com.thaynna.forumhub.domain.model.Curso;

public interface CursoRepository extends JpaRepository<Curso, Long> {
    
}
