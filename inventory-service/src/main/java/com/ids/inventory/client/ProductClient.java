package com.ids.inventory.client;

import com.ids.inventory.dto.ProductResponse;
import com.ids.inventory.exception.ResourceNotFoundException;
import org.springframework.http.HttpStatusCode;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;

@Component
public class ProductClient {

    private final RestClient productRestClient;

    public ProductClient(RestClient productRestClient) {
        this.productRestClient = productRestClient;
    }

    public ProductResponse findProductById(Long productId) {
        ProductResponse product = productRestClient.get()
                .uri("/api/products/{id}", productId)
                .retrieve()
                .onStatus(HttpStatusCode::is4xxClientError, (request, response) -> {
                    throw new ResourceNotFoundException("Producto no encontrado con ID: " + productId);
                })
                .body(ProductResponse.class);

        if (product == null) {
            throw new ResourceNotFoundException("Producto no encontrado con ID: " + productId);
        }

        return product;
    }
}