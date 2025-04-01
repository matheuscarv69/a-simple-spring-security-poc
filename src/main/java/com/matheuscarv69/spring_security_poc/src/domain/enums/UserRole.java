package com.matheuscarv69.spring_security_poc.src.domain.enums;

public enum UserRole {

    ADMIN("ROLE_ADMIN"),
    USER("ROLE_USER");

    private final String role;

    UserRole(String role) {
        this.role = role;
    }

    String getRole() {
        return role;
    }

}
