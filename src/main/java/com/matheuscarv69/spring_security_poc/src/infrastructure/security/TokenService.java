package com.matheuscarv69.spring_security_poc.src.infrastructure.security;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTCreationException;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.matheuscarv69.spring_security_poc.src.domain.model.user.User;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;

/*
 * TokenService.java
 * This class is responsible for generating and validating JWT tokens.
 * It uses the auth0 library to create and verify tokens.
 *
 * @author Matheus Carvalho
 * @version 1.0
 * @since 2025-04-01
 */
@Slf4j
@Service
public class TokenService {

    @Value("${api.security.token.secret}")
    private String secret;

    /**
     * 1. Generates a JWT token for the given user.
     *
     * @param user The user for whom the token is generated.
     * @return The generated JWT token.
     */
    public String generateToken(User user) {
        log.info("TOKEN SERVICE - generateToken - Starting token generation for user: {}", user.getUsername());

        try {
            log.info("TOKEN SERVICE - generateToken - Secret: {}", secret);
            Algorithm algorithm = Algorithm.HMAC256(secret);

            String token = JWT.create()
                    .withIssuer("auth-API")
                    .withSubject(user.getUsername())
                    .withClaim("id", user.getId())
                    .withClaim("role", user.getRole().getRole())
                    .withExpiresAt(generateExpirationDate())
                    .sign(algorithm);

            log.info("TOKEN SERVICE - generateToken - Token successfully generated for user: {}", user.getUsername());
            return token;

        } catch (JWTCreationException exception) {
            log.error("TOKEN SERVICE - generateToken - Error generating token for user: {}", user.getUsername(), exception);
            throw new RuntimeException("Error while generating token", exception);
        }
    }

    /**
     * 2. Validates the given JWT token.
     *
     * @param token The JWT token to be validated.
     * @return The validated token if valid, otherwise an empty string.
     */
    public String validateToken(String token) {

        log.info("TOKEN SERVICE - validateToken - Validating token: {}", token);
        log.info("TOKEN SERVICE - validateToken - Secret: {}", secret);

        try {
            Algorithm algorithm = Algorithm.HMAC256(secret);

            var username = JWT.require(algorithm)
                    .withIssuer("auth-API")
                    .build()
                    .verify(token)
                    .getSubject();

            log.info("TOKEN SERVICE - validateToken - Valid token, user: {}", username);
            return username;

        } catch (JWTVerificationException exception) {

            log.error("TOKEN SERVICE - validateToken - Invalid token: {}", token, exception);

            // Optionally, you can return a specific message or throw an exception
            // throw new RuntimeException("Invalid token", exception);
            return "";

        }
    }

    private Instant generateExpirationDate() {

        log.info("TOKEN SERVICE - generateExpirationDate - Generating token expiration date");
        return LocalDateTime.now().plusHours(3).toInstant(ZoneOffset.of("-03:00"));

    }

}