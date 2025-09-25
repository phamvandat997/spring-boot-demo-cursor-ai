package com.example.dto;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class ProductDto {
    private Long id;
    private String name;
    private String desc;
    private String sku;
    private Long categoryId;
    private Long inventoryId;
    private BigDecimal price;
}

