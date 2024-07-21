package com.example.produto_service.infra.web;

import com.example.produto_service.domain.Product;
import com.example.produto_service.domain.ProductRepository;
import com.example.produto_service.infra.client.ReviewClient;
import com.example.produto_service.infra.client.ReviewResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/products")
@RequiredArgsConstructor
public class ProductController {
    private final ProductRepository products;
    private final ReviewClient reviews;

    @GetMapping
    public List<ProductResponse> getAll() {
        return products.getAll()
                .stream()
                .map(this::toResponse)
                .collect(Collectors.toList());
    }

    @GetMapping("/{productId}")
    public ProductResponse getOne(@PathVariable Long productId) {
        return products.getOne(productId)
                .map(this::toResponse)
                .orElseThrow(ResourceNotFoundException::new);
    }

    private ProductResponse toResponse(Product product) {
        return ProductResponse.of(product, fingReviewsByProductId(product.getId()));
    }

    private List<ReviewResponse> fingReviewsByProductId(Long productId) {
        return reviews.reviewsByProductId(productId);
    }
}
