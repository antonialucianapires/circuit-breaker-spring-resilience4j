package com.example.produto_service.infra.client;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.*;

@Log4j2
@Component
@RequiredArgsConstructor
public class ReviewClientImpl implements ReviewClient {

    private final RestTemplate restTemplate;
    private final static String API_URL = UriComponentsBuilder
            .fromHttpUrl("http://localhost:8082/reviews")
            .queryParam("productId", "{productId}")
            .encode()
            .toUriString();
    @Override
    public List<ReviewResponse> reviewsByProductId(Long productId) {
        return request(productId);
    }

    private List<ReviewResponse> request(Long productId) {
       Map<String, Object> params = Map.of("productId", productId);
       ReviewResponse[] reviews;
       log.info("Requesting reviews from API for product id: {}", productId);
        try {
            reviews = restTemplate.getForObject(API_URL, ReviewResponse[].class, params);
        } catch (Exception e) {
            log.error("Error getting reviews from API", e);
            throw new RuntimeException("Error getting reviews from API", e);
        }
        return reviews == null ? List.of() : Arrays.asList(reviews);
    }
}
