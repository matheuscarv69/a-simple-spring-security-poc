package com.matheuscarv69.spring_security_poc.src.domain.model.user;

public record AuthenticationDTO(
        String username,
        String password
) {
}
