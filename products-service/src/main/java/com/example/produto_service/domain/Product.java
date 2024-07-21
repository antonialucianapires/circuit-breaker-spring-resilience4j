package com.example.produto_service.domain;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Product {
    private Long id;
    private String name;

    public Product(String name) {
        this.name = name;
    }
}
