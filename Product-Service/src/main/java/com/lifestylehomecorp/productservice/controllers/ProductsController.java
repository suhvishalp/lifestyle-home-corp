package com.lifestylehomecorp.productservice.controllers;


import com.commercetools.api.models.product.ProductPagedQueryResponse;
import com.lifestylehomecorp.productservice.services.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.concurrent.CompletableFuture;

@RestController
@RequestMapping("/products")
public class ProductsController {

    private final ProductService productService;

    @Autowired
    public ProductsController(ProductService productService) {
        this.productService = productService;
    }

    @GetMapping
    public CompletableFuture<ResponseEntity<ProductPagedQueryResponse>> getProducts() {
        return productService.fetchProducts()
                .thenApply(response -> new ResponseEntity<>(response, HttpStatus.OK))
                .exceptionally(ex -> {
                    // Log exception if needed
                    return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
                });
    }
}