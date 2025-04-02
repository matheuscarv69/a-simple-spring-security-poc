package com.matheuscarv69.spring_security_poc.src.application.controller;

import com.matheuscarv69.spring_security_poc.src.domain.model.user.AuthenticationDTO;
import com.matheuscarv69.spring_security_poc.src.domain.model.user.User;
import com.matheuscarv69.spring_security_poc.src.domain.model.user.UserInputDTO;
import com.matheuscarv69.spring_security_poc.src.domain.service.UserService;
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

@Slf4j
@RestController
@RequestMapping("/v1/auth")
@RequiredArgsConstructor
public class AuthenticationController {

    private final AuthenticationManager authenticationManager;
    private final UserService userService;

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody @Valid AuthenticationDTO data) {

        log.info("AUTHENTICATION CONTROLLER - login - Iniciando login para o usuário: {}", data.username());

        var usernamePassword = new UsernamePasswordAuthenticationToken(data.username(), data.password());
        var auth = authenticationManager.authenticate(usernamePassword);

        log.info("AUTHENTICATION CONTROLLER - login - Usuário autenticado com sucesso: {}", auth);

        return ResponseEntity.ok(auth);
    }

    @PostMapping("/register")
    public ResponseEntity<User> register(@RequestBody @Valid UserInputDTO dataInput) {

        log.info("AUTHENTICATION CONTROLLER - register - Iniciando registro de usuário: {}", dataInput);

        User savedUser = userService.save(dataInput);

        log.info("AUTHENTICATION CONTROLLER - register - Usuário registrado: {}", savedUser);

        return ResponseEntity.ok(savedUser);

    }

}