package com.matheuscarv69.spring_security_poc.src.domain.model.user;

public record UserInputDTO(
        String username,
        String password,
        UserRole role
) {
}
