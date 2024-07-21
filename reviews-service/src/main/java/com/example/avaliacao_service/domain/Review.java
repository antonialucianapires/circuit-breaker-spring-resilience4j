package com.example.avaliacao_service.domain;

import lombok.*;

@Setter
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Review {
    private Long id;
    private Integer score;
    private String description;
    private String reviewerName;
    private Long productId;
}
