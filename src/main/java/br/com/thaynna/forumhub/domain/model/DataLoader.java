package br.com.thaynna.forumhub.domain.model;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import br.com.thaynna.forumhub.domain.repository.UsuarioRepository;

@Component
public class DataLoader implements CommandLineRunner {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public void run(String... args) throws Exception {
        if(usuarioRepository.count() == 0) {
            Usuario usuario = new Usuario();
            usuario.setNome("Thay");
            usuario.setEmail("thay@email.com");
            usuario.setSenha(passwordEncoder.encode("123456"));
            usuarioRepository.save(usuario);
        }
    }
}

