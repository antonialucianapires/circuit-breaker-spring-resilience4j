package com.example.produto_service.infra.web;

import com.example.produto_service.domain.Product;
import com.example.produto_service.infra.client.ReviewResponse;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ProductResponse {
    public Long id;
    public String name;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    public List<ReviewResponse> reviews;

    public static ProductResponse of(Product product) {
        return builder()
                .id(product.getId())
                .name(product.getName())
                .build();
    }

    public static ProductResponse of(Product product, List<ReviewResponse> reviews) {
        return builder()
                .id(product.getId())
                .name(product.getName())
                .reviews(reviews)
                .build();
    }
}
