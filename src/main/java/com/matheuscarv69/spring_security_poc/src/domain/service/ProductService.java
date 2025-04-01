package com.matheuscarv69.spring_security_poc.src.domain.service;

import com.matheuscarv69.spring_security_poc.src.domain.model.Product;
import com.matheuscarv69.spring_security_poc.src.domain.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class ProductService {

    private final ProductRepository productRepository;

    public List<Product> listProducts() {
        log.info("PRODUCT SERVICE - listProducts - Iniciando listagem de produtos");

        List<Product> products = productRepository.findAll();

        log.info("PRODUCT SERVICE - listProducts - Finalizando listagem de produtos");

        return products;
    }

    public Product addProduct(Product product) {

        log.info("PRODUCT SERVICE - addProduct - Iniciando adição de produto: {}", product);

        Product savedProduct = productRepository.save(product);

        log.info("PRODUCT SERVICE - addProduct - Produto adicionado: {}", savedProduct);

        return savedProduct;
    }

}