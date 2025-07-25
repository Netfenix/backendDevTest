package com.example.yourapp.service;

import com.example.yourapp.client.ProductClient;
import com.example.yourapp.model.ProductDetail;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ProductService {

    private final ProductClient productClient;

    public ProductService(ProductClient productClient) {
        this.productClient = productClient;
    }

    public List<ProductDetail> getSimilarProducts(String productId) {
        productClient.validateProductExists(productId);
        List<String> similarIds = productClient.getSimilarProductIds(productId);

        return similarIds.parallelStream()
                .map(productClient::getProductDetail)
                .filter(Optional::isPresent)
                .map(Optional::get)
                .collect(Collectors.toList());
    }
}
