package br.com.thaynna.forumhub.domain.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import br.com.thaynna.forumhub.domain.model.Usuario;

public interface UsuarioRepository extends JpaRepository<Usuario, Long> {
    Optional<Usuario> findByEmail(String email);
}
