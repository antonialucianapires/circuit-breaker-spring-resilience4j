package com.example.produto_service.infra.client;

import java.util.List;

public interface ReviewClient {
    List<ReviewResponse> reviewsByProductId(Long id);
}
