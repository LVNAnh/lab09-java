package com.example.webservice.repository;

import com.example.webservice.model.Order;
import com.example.webservice.model.User;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;
import java.util.Optional;

public interface OrderRepository extends MongoRepository<Order, String> {
    Optional<Order> findByOrderNumber(String orderNumber);

    List<Order> findByUser(User user);
}