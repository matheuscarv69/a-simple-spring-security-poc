package com.matheuscarv69.spring_security_poc.src.application.controller;


import com.matheuscarv69.spring_security_poc.src.domain.model.Product;
import com.matheuscarv69.spring_security_poc.src.domain.service.ProductService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/v1/products")
public class ProductsController {

    private final ProductService productService;

    @PostMapping
    public ResponseEntity<Product> addProduct(@RequestBody Product product) {

        log.info("PRODUCTS CONTROLLER - addProduct - Starting to add product: {}", product);

        Product savedProduct = productService.addProduct(product);

        log.info("PRODUCTS CONTROLLER - addProduct - Product added: {}", savedProduct);

        return ResponseEntity.ok(savedProduct);

    }

    @GetMapping
    public ResponseEntity<List<Product>> listProducts() {

        log.info("PRODUCTS CONTROLLER - listProducts - Starting to list products");

        List<Product> products = productService.listProducts();

        log.info("PRODUCTS CONTROLLER - listProducts - Finished listing products");

        return ResponseEntity.ok(products);
    }

}