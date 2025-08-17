package br.com.thaynna.forumhub.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import org.springframework.security.crypto.password.PasswordEncoder;

import br.com.thaynna.forumhub.domain.dto.LoginDTO;
import br.com.thaynna.forumhub.domain.model.Usuario;
import br.com.thaynna.forumhub.domain.repository.UsuarioRepository;
import br.com.thaynna.forumhub.service.TokenService;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/login")
public class AutenticacaoController {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private TokenService tokenService;

    @PostMapping
    public String autenticar(@RequestBody @Valid LoginDTO dto) {
        Usuario usuario = usuarioRepository.findByEmail(dto.email())
                .orElseThrow(() -> new RuntimeException("Usuário ou senha inválidos"));

        if(!passwordEncoder.matches(dto.senha(), usuario.getSenha())) {
            throw new RuntimeException("Usuário ou senha inválidos");
        }

        return tokenService.gerarToken(usuario.getEmail());
    }
}
