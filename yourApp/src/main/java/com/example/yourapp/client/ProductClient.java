package com.example.yourapp.client;

import com.example.yourapp.model.ProductDetail;
import com.example.yourapp.exception.ProductNotFoundException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@Component
public class ProductClient {

    private final RestTemplate restTemplate;
    private final String baseUrl;

    public ProductClient(RestTemplate restTemplate, @Value("${external.api.base-url}") String baseUrl) {
        this.restTemplate = restTemplate;
        this.baseUrl = baseUrl;
    }

    public List<String> getSimilarProductIds(String productId) {
        String url = baseUrl + "/product/" + productId + "/similarids";
        try {
            return Arrays.asList(restTemplate.getForObject(url, String[].class));
        } catch (HttpClientErrorException.NotFound e) {
            throw new ProductNotFoundException("Product " + productId + " not found");
        }
    }

    public Optional<ProductDetail> getProductDetail(String productId) {
        String url = baseUrl + "/product/" + productId;
        try {
            return Optional.ofNullable(restTemplate.getForObject(url, ProductDetail.class));
        } catch (HttpClientErrorException.NotFound e) {
            return Optional.empty(); // Silenciar errores individuales
        }
    }

    public void validateProductExists(String productId) {
        String url = baseUrl + "/product/" + productId;
        try {
            restTemplate.getForObject(url, ProductDetail.class);
        } catch (HttpClientErrorException.NotFound e) {
            throw new ProductNotFoundException("Product " + productId + " not found");
        }
    }
}
