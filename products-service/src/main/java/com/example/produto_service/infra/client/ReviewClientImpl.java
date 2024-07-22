package com.example.produto_service.infra.client;

import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

@Log4j2
@Component
@RequiredArgsConstructor
public class ReviewClientImpl implements ReviewClient {

    private final RestTemplate restTemplate;
    private static final ConcurrentHashMap<Long, List<ReviewResponse>> reviewCache = new ConcurrentHashMap<>();
    private static final String API_URL = UriComponentsBuilder
            .fromHttpUrl("http://localhost:8082/reviews")
            .queryParam("productId", "{productId}")
            .encode()
            .toUriString();

    @Override
    @CircuitBreaker(name = "reviewsCB", fallbackMethod = "retrieveReviewsFromCache")
    public List<ReviewResponse> getReviewsByProductId(Long productId) {
        return fetchReviewsFromAPI(productId);
    }

    private List<ReviewResponse> fetchReviewsFromAPI(Long productId) {
        Map<String, Object> params = Map.of("productId", productId);
        ReviewResponse[] reviews = null;
        log.info("Requesting reviews from API for product id: {}", productId);
        try {
            reviews = restTemplate.getForObject(API_URL, ReviewResponse[].class, params);
        } catch (Exception exception) {
            log.error("Failed to fetch reviews from API for product id: {}", productId, exception);
            throw exception;
        }
        List<ReviewResponse> reviewsList = Arrays.asList(reviews);
        log.info("Caching reviews for product id: {}", productId);
        reviewCache.put(productId, reviewsList);
        return reviewsList;
    }

    private List<ReviewResponse> retrieveReviewsFromCache(Long productId, Throwable throwable) {
        log.error("Fallback to cache due to API error for product id: {}", productId, throwable);
        return reviewCache.getOrDefault(productId, List.of());
    }
}