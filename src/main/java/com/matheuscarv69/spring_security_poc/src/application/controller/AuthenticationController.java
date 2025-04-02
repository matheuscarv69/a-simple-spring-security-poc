package com.matheuscarv69.spring_security_poc.src.application.controller;

import com.matheuscarv69.spring_security_poc.src.domain.model.user.AuthenticationDTO;
import com.matheuscarv69.spring_security_poc.src.domain.model.user.LoginResponseDTO;
import com.matheuscarv69.spring_security_poc.src.domain.model.user.User;
import com.matheuscarv69.spring_security_poc.src.domain.model.user.UserInputDTO;
import com.matheuscarv69.spring_security_poc.src.domain.service.UserService;
import com.matheuscarv69.spring_security_poc.src.infrastructure.security.TokenService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/*
 * AuthenticationController.java
 * This class is responsible for handling authentication-related requests.
 * It provides endpoints for user login and registration.
 *
 * @author Matheus Carvalho
 * @version 1.0
 * @since 2025-04-01
 */
@Slf4j
@RestController
@RequestMapping("/v1/auth")
@RequiredArgsConstructor
public class AuthenticationController {

    private final AuthenticationManager authenticationManager;
    private final UserService userService;
    private final TokenService tokenService;

    @PostMapping("/login")
    public ResponseEntity<LoginResponseDTO> login(@RequestBody @Valid AuthenticationDTO data) {

        log.info("AUTHENTICATION CONTROLLER - login - Starting login for user: {}", data.username());

        var usernamePassword = new UsernamePasswordAuthenticationToken(data.username(), data.password());
        var auth = authenticationManager.authenticate(usernamePassword);

        var token = tokenService.generateToken((User) auth.getPrincipal());

        log.info("AUTHENTICATION CONTROLLER - login - User successfully authenticated: {}", auth);

        return ResponseEntity.ok(new LoginResponseDTO(token));
    }

    @PostMapping("/register")
    public ResponseEntity<User> register(@RequestBody @Valid UserInputDTO dataInput) {

        log.info("AUTHENTICATION CONTROLLER - register - Starting user registration: {}", dataInput);

        User savedUser = userService.save(dataInput);

        log.info("AUTHENTICATION CONTROLLER - register - User registered: {}", savedUser);

        return ResponseEntity.ok(savedUser);

    }

}