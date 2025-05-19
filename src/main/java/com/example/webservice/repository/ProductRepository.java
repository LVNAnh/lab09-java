package com.example.webservice.repository;

import com.example.webservice.model.Product;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;

public interface ProductRepository extends MongoRepository<Product, String> {
    Optional<Product> findByCode(String code);

    Boolean existsByCode(String code);
}