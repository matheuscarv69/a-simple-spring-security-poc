package com.matheuscarv69.spring_security_poc.src.infrastructure.security;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Slf4j
@Configuration
@EnableWebSecurity
public class SecurityConfiguration {

    /**
     * 1. Configures the security filter for the application.
     * Defines authorization rules and disables CSRF.
     *
     * @param httpSecurity HTTP security configuration.
     * @return The configured SecurityFilterChain.
     * @throws Exception If an error occurs while configuring the security filter.
     */
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {

        return httpSecurity
                .csrf(csrf -> csrf.disable())
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authorizeHttpRequests(authorize -> authorize
                        .requestMatchers(HttpMethod.POST, "/v1/auth/login")
                        .permitAll()
                        .requestMatchers(HttpMethod.POST, "/v1/auth/register")
                        .permitAll()
                        .requestMatchers(HttpMethod.POST, "/v1/products")
                        .hasRole("ADMIN")
                        .anyRequest().authenticated()
                )
                .build();
    }

    /**
     * 2. Configures the AuthenticationManager for user authentication.
     * Used in the AuthenticationController.
     *
     * @param authenticationConfiguration Authentication configuration.
     * @return The configured AuthenticationManager.
     * @throws Exception If an error occurs while configuring the AuthenticationManager.
     */
    @Bean
    public AuthenticationManager authenticationManager(
            AuthenticationConfiguration authenticationConfiguration) throws Exception {

        return authenticationConfiguration.getAuthenticationManager();

    }

    /**
     * 3. Configures the PasswordEncoder for password hashing.
     * Used in the UserService.
     *
     * @return The configured PasswordEncoder.
     */
    @Bean
    public PasswordEncoder passwordEncoder() {

        return new BCryptPasswordEncoder();

    }

}
