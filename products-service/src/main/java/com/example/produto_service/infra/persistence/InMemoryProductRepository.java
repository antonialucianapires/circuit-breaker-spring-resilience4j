package com.example.produto_service.infra.persistence;

import com.example.produto_service.domain.Product;
import com.example.produto_service.domain.ProductRepository;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;
import java.util.stream.Collectors;

@Component
public class InMemoryProductRepository implements ProductRepository {

    private static final ConcurrentHashMap<Long, Product> PRODUCTS = new ConcurrentHashMap<>();
    private static final AtomicLong idGenerator = new AtomicLong(1);

    static {
        addProduct(new Product("Desktop 4GB"));
        addProduct(new Product("Notebook 4GB"));
        addProduct(new Product("Notebook 8GB"));
    }

    private static void addProduct(Product product) {
        long id = idGenerator.getAndIncrement();
        product.setId(id);
        PRODUCTS.put(id, product);
    }

    @Override
    public void save(Product product) {
        if (product.getId() == null) {
            long newId = idGenerator.getAndIncrement();
            product.setId(newId);
        }
        PRODUCTS.put(product.getId(), product);
    }

    @Override
    public Optional<Product> getOne(Long id) {
        return Optional.ofNullable(PRODUCTS.get(id));
    }

    @Override
    public List<Product> getAll() {
        return PRODUCTS.values()
                .stream()
                .collect(Collectors.toList());
    }
}
