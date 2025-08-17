package br.com.thaynna.forumhub.service;

import java.sql.Date;

import org.springframework.stereotype.Service;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;


@Service
public class TokenService {

    private final JwtProperties jwtProperties;

    public TokenService(JwtProperties jwtProperties) {
        this.jwtProperties = jwtProperties;
    }

    public String gerarToken(String email) {
        Algorithm algorithm = Algorithm.HMAC256(jwtProperties.getSecret());
        return JWT.create()
                .withSubject(email)
                .withExpiresAt(new Date(System.currentTimeMillis() + jwtProperties.getExpiration()))
                .sign(algorithm);
    }

    public String getEmail(String token) {
        Algorithm algorithm = Algorithm.HMAC256(jwtProperties.getSecret());
        DecodedJWT decodedJWT = JWT.require(algorithm).build().verify(token);
        return decodedJWT.getSubject();
    }

    public boolean isTokenValido(String token) {
        try {
            getEmail(token);
            return true;
        } catch (Exception e) {
            return false;
        }
    }
}
