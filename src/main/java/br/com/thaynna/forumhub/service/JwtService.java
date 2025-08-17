package br.com.thaynna.forumhub.service;

import java.sql.Date;

import org.springframework.stereotype.Component;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import java.security.Key;

@Component
public class JwtService {

    private static final Key SECRET_KEY = Keys.hmacShaKeyFor("minhaChaveSuperSecretaComMaisDe32Caracteres!".getBytes());


    public String gerarToken(String email) {
    return Jwts.builder()
            .setSubject(email)
            .setExpiration(new Date(System.currentTimeMillis() + 86400000)) // 1 dia
            .signWith(SECRET_KEY)
            .compact();
}
}
