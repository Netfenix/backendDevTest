package com.example.yourapp.controller;

import com.example.yourapp.model.ProductDetail;
import com.example.yourapp.service.ProductService;
import com.example.yourapp.exception.ProductNotFoundException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/product")
public class ProductController {

    private final ProductService productService;

    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @GetMapping("/{productId}/similar")
    public ResponseEntity<List<ProductDetail>> getSimilarProducts(@PathVariable String productId) {
        List<ProductDetail> result = productService.getSimilarProducts(productId);
        return ResponseEntity.ok(result);
    }

    @ExceptionHandler(ProductNotFoundException.class)
    public ResponseEntity<Void> handleNotFound() {
        return ResponseEntity.notFound().build();
    }
}
