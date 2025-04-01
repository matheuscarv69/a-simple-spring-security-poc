package com.matheuscarv69.spring_security_poc.src.domain.repository;


import com.matheuscarv69.spring_security_poc.src.domain.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {
}