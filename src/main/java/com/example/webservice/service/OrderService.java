package com.example.webservice.service;

import com.example.webservice.dto.OrderDto;
import com.example.webservice.exception.BadRequestException;
import com.example.webservice.exception.ResourceNotFoundException;
import com.example.webservice.model.Order;
import com.example.webservice.model.OrderItem;
import com.example.webservice.model.Product;
import com.example.webservice.model.User;
import com.example.webservice.repository.OrderRepository;
import com.example.webservice.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
public class OrderService {

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private UserService userService;

    public List<Order> getAllOrders() {
        return orderRepository.findAll();
    }

    public Order getOrderById(String id) {
        return orderRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Order not found with id: " + id));
    }

    public Order createOrder(OrderDto orderDto) {
        User currentUser = userService.getCurrentUser();

        if (orderDto.getItems() == null || orderDto.getItems().isEmpty()) {
            throw new BadRequestException("Order must contain at least one item");
        }

        Order order = new Order();
        order.setOrderNumber(generateOrderNumber());
        order.setCreatedAt(LocalDateTime.now());
        order.setUser(currentUser);

        List<OrderItem> orderItems = new ArrayList<>();
        BigDecimal totalPrice = BigDecimal.ZERO;

        for (OrderDto.OrderItemDto itemDto : orderDto.getItems()) {
            Product product = productRepository.findById(itemDto.getProductId())
                    .orElseThrow(() -> new ResourceNotFoundException(
                            "Product not found with id: " + itemDto.getProductId()));

            OrderItem orderItem = new OrderItem();
            orderItem.setId(UUID.randomUUID().toString()); // Generate ID for MongoDB
            orderItem.setProduct(product);
            orderItem.setQuantity(itemDto.getQuantity());
            orderItem.setPrice(product.getPrice());
            orderItems.add(orderItem);

            totalPrice = totalPrice.add(product.getPrice().multiply(BigDecimal.valueOf(itemDto.getQuantity())));
        }

        order.setTotalPrice(totalPrice);
        order.setItems(orderItems);

        return orderRepository.save(order);
    }

    public Order updateOrder(String id, OrderDto orderDto) {
        Order order = getOrderById(id);

        // Clear existing items
        order.getItems().clear();

        if (orderDto.getItems() == null || orderDto.getItems().isEmpty()) {
            throw new BadRequestException("Order must contain at least one item");
        }

        BigDecimal totalPrice = BigDecimal.ZERO;

        for (OrderDto.OrderItemDto itemDto : orderDto.getItems()) {
            Product product = productRepository.findById(itemDto.getProductId())
                    .orElseThrow(() -> new ResourceNotFoundException(
                            "Product not found with id: " + itemDto.getProductId()));

            OrderItem orderItem = new OrderItem();
            orderItem.setId(UUID.randomUUID().toString()); // Generate ID for MongoDB
            orderItem.setProduct(product);
            orderItem.setQuantity(itemDto.getQuantity());
            orderItem.setPrice(product.getPrice());
            order.getItems().add(orderItem);

            totalPrice = totalPrice.add(product.getPrice().multiply(BigDecimal.valueOf(itemDto.getQuantity())));
        }

        order.setTotalPrice(totalPrice);

        return orderRepository.save(order);
    }

    public void deleteOrder(String id) {
        Order order = getOrderById(id);
        orderRepository.delete(order);
    }

    private String generateOrderNumber() {
        return "ORD-" + UUID.randomUUID().toString().substring(0, 8).toUpperCase();
    }
}