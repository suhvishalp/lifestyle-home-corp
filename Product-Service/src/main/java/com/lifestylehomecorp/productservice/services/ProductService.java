package com.lifestylehomecorp.productservice.services;

import com.commercetools.api.client.ProjectApiRoot;
import com.commercetools.api.models.product.ProductPagedQueryResponse;
import com.commercetools.api.models.product.ProductProjectionPagedQueryResponse;
import com.commercetools.api.models.product.ProductProjectionPagedSearchResponse;
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

    // Fetch all products with category key 'home-decor' asynchronously using /products endpoint
    public CompletableFuture<ProductPagedQueryResponse> fetchProductsByCategoryHomeDecor() {
        return apiRoot
                .products()
                .get()
                .withWhere("categories(key=\"home-decor\")")
                .execute()
                .toCompletableFuture()
                .thenApply(ApiHttpResponse::getBody)
                .exceptionally(ex -> {
                    System.err.println("Error fetching products by category 'home-decor': " + ex.getMessage());
                    throw new RuntimeException("Failed to fetch products by category 'home-decor'");
                });
    }

    // Fetch all products from category 'furniture' with price >= 25.00 EUR, staged=false using /product-projections endpoint
    public CompletableFuture<ProductProjectionPagedQueryResponse> fetchProductProjectionsByFurnitureAndPrice() {
        return apiRoot
                .productProjections()
                .get()
                .withWhere("categories(key=\"furniture\") and price(centAmount >= 2500)")  // Filter by category and price
                .withStaged(false)  // Pass 'staged' as a query parameter
                .execute()
                .toCompletableFuture()
                .thenApply(ApiHttpResponse::getBody)
                .exceptionally(ex -> {
                    System.err.println("Error fetching product projections: " + ex.getMessage());
                    throw new RuntimeException("Failed to fetch product projections for 'furniture' with price >= 25.00 EUR");
                });
    }

    // Fetch store-specific products asynchronously using storeProjection key from /product-projections/search
    public CompletableFuture<ProductProjectionPagedSearchResponse> fetchStoreSpecificProducts(String storeKey) {
        return apiRoot
                .productProjections()
                .search()
                .get()
                .addQueryParam("storeProjection", storeKey)  // Add 'storeProjection' as a query parameter
                .withLimit(20)  // Optionally limit the number of results
                .execute()
                .toCompletableFuture()
                .thenApply(ApiHttpResponse::getBody)
                .exceptionally(ex -> {
                    System.err.println("Error fetching store-specific products: " + ex.getMessage());
                    throw new RuntimeException("Failed to fetch products for store: " + storeKey);
                });
    }

    // Fetch products using the recently introduced Product Search API, with 2-3 examples using query expressions
//    public CompletableFuture<ProductPagedSearchResponse> searchProductsUsingQuery(String queryExpression) {
//        return null;
//    }

    // Example 1: Search products by name and description containing a specific term
//    public CompletableFuture<ProductPagedSearchResponse> searchProductsByNameAndDescription(String searchTerm) {
//        String queryExpression = "name.en=\"" + searchTerm + "\" OR description.en=\"" + searchTerm + "\"";
//        return searchProductsUsingQuery(queryExpression);
//    }

//    // Example 2: Search products with a price range and availability status
//    public CompletableFuture<ProductPagedSearchResponse> searchProductsByPriceRange(double minPrice, double maxPrice) {
//        String queryExpression = "price(centAmount >= " + (int)(minPrice * 100) + " and centAmount <= " + (int)(maxPrice * 100) + ")";
//        return searchProductsUsingQuery(queryExpression);
//    }

    // Example 3: Search products by custom attributes (e.g., color or size)
//    public CompletableFuture<ProductPagedSearchResponse> searchProductsByCustomAttribute(String attributeName, String attributeValue) {
//        String queryExpression = "custom.fields." + attributeName + "=\"" + attributeValue + "\"";
//        return searchProductsUsingQuery(queryExpression);
//    }

}