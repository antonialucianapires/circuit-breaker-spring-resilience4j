package com.example.avaliacao_service.infra.persistence;

import com.example.avaliacao_service.domain.Review;
import com.example.avaliacao_service.domain.ReviewRepository;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;
import java.util.stream.Collectors;

@Component
public class InMemoryReviewRepository implements ReviewRepository {
    private static final ConcurrentHashMap<Long, Review> REVIEWS = new ConcurrentHashMap<>();
    private static final AtomicLong idGenerator = new AtomicLong(1);

    static {
        addReview(Review.builder()
                .score(10)
                .reviewerName("Thiago")
                .description("Produto superou minhas expectativas.")
                .productId(1L)
                .build());

        addReview(Review.builder()
                .score(1)
                .reviewerName("Alexandre")
                .description("Veio com defeito.")
                .productId(1L)
                .build());

        addReview(Review.builder()
                .score(4)
                .reviewerName("Maria")
                .description("Computador trava muito.")
                .productId(1L)
                .build());

        addReview(Review.builder()
                .score(8)
                .reviewerName("Daniel")
                .description("Quase perfeito.")
                .productId(2L)
                .build());

        addReview(Review.builder()
                .score(5)
                .reviewerName("Alex")
                .description("Não vem com sistema operacional.")
                .productId(3L)
                .build());
    }

    private static void addReview(Review review) {
        long id = idGenerator.getAndIncrement();
        review.setId(id);
        REVIEWS.put(id, review);
    }

    @Override
    public void save(Review review) {
        if (review.getId() == null) {
            review.setId(idGenerator.getAndIncrement());
        }
        REVIEWS.put(review.getId(), review);
    }

    @Override
    public Optional<Review> getOne(Long id) {
        return Optional.ofNullable(REVIEWS.get(id));
    }

    @Override
    public List<Review> getAll() {
        return REVIEWS.values()
                .stream()
                .collect(Collectors.toList());
    }
}
