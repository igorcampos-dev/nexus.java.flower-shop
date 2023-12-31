package com.flowershop.back.services.impl;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTCreationException;
import com.auth0.jwt.exceptions.JWTDecodeException;
import com.auth0.jwt.exceptions.TokenExpiredException;
import com.flowershop.back.services.TokenService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;

@Service
public class TokenServiceImpl implements TokenService {
    @Value("${api.security.token.secret}")
    private String secret;


    @Override
    public String generateToken(UserDetails user) throws JWTCreationException{
            Algorithm algorithm = Algorithm.HMAC256(secret);
            return JWT.create()
                    .withIssuer("auth-api")
                    .withSubject(user.getUsername())
                    .withExpiresAt(genExpirationDate())
                    .sign(algorithm);
    }

    @Override
    public String validateToken(String token) {
        try {
            Algorithm algorithm = Algorithm.HMAC256(secret);
            return JWT.require(algorithm)
                    .withIssuer("auth-api")
                    .build()
                    .verify(token)
                    .getSubject();

        } catch (TokenExpiredException e) {
            throw new TokenExpiredException("Erro ao validar o token: " + e.getMessage(), Instant.now());
        } catch (JWTDecodeException e) {
            throw new JWTDecodeException("Token inválido, está corrompido: " + e.getMessage());
        } catch (Exception e) {
            throw new RuntimeException("Erro ao processar: " + e.getMessage());
        }
    }

    private Instant genExpirationDate(){
        return LocalDateTime.now().plusHours(2).toInstant(ZoneOffset.of("-03:00"));
    }

}
