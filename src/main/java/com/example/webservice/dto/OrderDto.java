package com.example.webservice.dto;

import lombok.Data;

import jakarta.validation.constraints.NotEmpty;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Data
public class OrderDto {
    private Long id;
    private String orderNumber;
    private BigDecimal totalPrice;
    private LocalDateTime createdAt;

    @NotEmpty
    private List<OrderItemDto> items;

    @Data
    public static class OrderItemDto {
        private Long productId;
        private int quantity;
    }
}