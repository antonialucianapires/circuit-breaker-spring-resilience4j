package com.example.avaliacao_service.infra.web;

import com.example.avaliacao_service.domain.Review;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ReviewResponse {
    private Long id;
    private Integer score;
    private String reviewerName;
    private String description;

    static ReviewResponse of(Review review) {
        return builder()
                .id(review.getId())
                .score(review.getScore())
                .description(review.getDescription())
                .reviewerName(review.getReviewerName())
                .build();
    }
}
