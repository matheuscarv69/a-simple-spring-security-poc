package com.matheuscarv69.spring_security_poc.src.domain.repository;

import com.matheuscarv69.spring_security_poc.src.domain.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    UserDetails findByUsername(String username);

}