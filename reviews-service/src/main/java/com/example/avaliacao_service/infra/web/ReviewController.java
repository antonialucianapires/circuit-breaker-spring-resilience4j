package com.example.avaliacao_service.infra.web;

import com.example.avaliacao_service.domain.ReviewRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/reviews")
@RequiredArgsConstructor
public class ReviewController {
    private final ReviewRepository reviews;
    @GetMapping
    public List<ReviewResponse> findByProductId(@RequestParam Long productId) {
        return reviews.getAll()
                .stream()
                .filter(review -> review.getProductId().equals(productId))
                .map(ReviewResponse::of)
                .collect(Collectors.toList());
    }
}
