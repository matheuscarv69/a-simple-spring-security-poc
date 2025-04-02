package com.matheuscarv69.spring_security_poc.src.domain.service;

import com.matheuscarv69.spring_security_poc.src.domain.model.user.User;
import com.matheuscarv69.spring_security_poc.src.domain.model.user.UserInputDTO;
import com.matheuscarv69.spring_security_poc.src.domain.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    public List<User> findAll() {
        log.info("USER SERVICE - findAll - Iniciando busca de todos os usuários");

        List<User> users = userRepository.findAll();

        log.info("USER SERVICE - findAll - Finalizando busca de todos os usuários");

        return users;
    }

    public Optional<User> findById(Long id) {
        log.info("USER SERVICE - findById - Iniciando busca de usuário com id: {}", id);

        Optional<User> user = userRepository.findById(id);

        log.info("USER SERVICE - findById - Finalizando busca de usuário com id: {}", id);

        return user;
    }

    public User save(UserInputDTO dataInput) {

        log.info("USER SERVICE - save - Iniciando salvamento de usuário: {}", dataInput);

        if (userRepository.findByUsername(dataInput.username()) != null) {

            log.error("USER SERVICE - save - Usuário já existe: {}", dataInput.username());
            throw new RuntimeException("Usuário já existe");

        }

        User newUser = User
                .builder()
                .username(dataInput.username())
                .password(new BCryptPasswordEncoder().encode(dataInput.password()))
                .role(dataInput.role())
                .build();

        User savedUser = userRepository.save(newUser);

        log.info("USER SERVICE - save - Usuário salvo: {}", savedUser);

        return savedUser;

    }

    public void deleteById(Long id) {

        log.info("USER SERVICE - deleteById - Iniciando exclusão de usuário com id: {}", id);

        userRepository.deleteById(id);

        log.info("USER SERVICE - deleteById - Usuário com id: {} excluído", id);

    }
}