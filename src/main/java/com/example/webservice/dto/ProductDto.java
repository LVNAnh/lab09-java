package com.example.webservice.dto;

import lombok.Data;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import java.math.BigDecimal;

@Data
public class ProductDto {
    private String id; // Đổi từ Long sang String

    @NotBlank
    private String code;

    @NotBlank
    private String name;

    @NotNull
    @Positive
    private BigDecimal price;

    private String illustration;

    private String description;
}