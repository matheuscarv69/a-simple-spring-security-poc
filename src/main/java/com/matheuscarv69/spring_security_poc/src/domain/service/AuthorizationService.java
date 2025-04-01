package com.matheuscarv69.spring_security_poc.src.domain.service;

import com.matheuscarv69.spring_security_poc.src.domain.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class AuthorizationService implements UserDetailsService {

    private final UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        log.info("AUTHORIZATION SERVICE: realizing authentication for user {}", username);

        var user = userRepository.findByUsername(username);

        log.info("AUTHORIZATION SERVICE: user found {}", user);

        return user;
    }

}
