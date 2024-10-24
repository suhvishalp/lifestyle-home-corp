package com.lifestylehomecorp.productservice.services;

import com.commercetools.api.client.ProjectApiRoot;
import com.commercetools.api.models.product.ProductPagedQueryResponse;
import io.vrap.rmf.base.client.ApiHttpResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.concurrent.CompletableFuture;

@Service
public class ProductService {

    private final ProjectApiRoot apiRoot;

    @Autowired
    public ProductService(ProjectApiRoot apiRoot) {
        this.apiRoot = apiRoot;
    }

    public CompletableFuture<ProductPagedQueryResponse> fetchProducts() {
        return apiRoot
                .products()
                .get()
                .execute()
                .toCompletableFuture()
                .thenApply(ApiHttpResponse::getBody)
                .exceptionally(ex -> {
                    // Handle exception
                    System.err.println("Error fetching products: " + ex.getMessage());
                    throw new RuntimeException("Failed to fetch products");
                });
    }
}