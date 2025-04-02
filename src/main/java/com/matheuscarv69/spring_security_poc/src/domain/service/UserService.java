package com.matheuscarv69.spring_security_poc.src.domain.service;

import com.matheuscarv69.spring_security_poc.src.domain.model.user.User;
import com.matheuscarv69.spring_security_poc.src.domain.model.user.UserInputDTO;
import com.matheuscarv69.spring_security_poc.src.domain.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UserDetails;
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
        log.info("USER SERVICE - findAll - Starting to fetch all users");

        List<User> users = userRepository.findAll();

        log.info("USER SERVICE - findAll - Finished fetching all users");

        return users;
    }

    public Optional<User> findById(Long id) {
        log.info("USER SERVICE - findById - Starting to fetch user with id: {}", id);

        Optional<User> user = userRepository.findById(id);

        log.info("USER SERVICE - findById - Finished fetching user with id: {}", id);

        return user;
    }

    public UserDetails findUserDetailsByUsername(String username) {

        log.info("USER SERVICE - findUserDetailsByUsername - Starting to fetch UserDetails with username: {}", username);

        UserDetails userDetails = userRepository.findByUsername(username);

        log.info("USER SERVICE - findUserDetailsByUsername - Finished fetching UserDetails with username: {}", username);

        return userDetails;

    }

    public User save(UserInputDTO dataInput) {
        log.info("USER SERVICE - save - Starting to save user: {}", dataInput);

        if (userRepository.findByUsername(dataInput.username()) != null) {
            log.error("USER SERVICE - save - User already exists: {}", dataInput.username());
            throw new RuntimeException("User already exists");
        }

        User newUser = User.builder()
                .username(dataInput.username())
                .password(new BCryptPasswordEncoder().encode(dataInput.password()))
                .role(dataInput.role())
                .build();

        User savedUser = userRepository.save(newUser);

        log.info("USER SERVICE - save - User saved: {}", savedUser);

        return savedUser;
    }

    public void deleteById(Long id) {
        log.info("USER SERVICE - deleteById - Starting to delete user with id: {}", id);

        userRepository.deleteById(id);

        log.info("USER SERVICE - deleteById - User with id: {} deleted", id);
    }

}