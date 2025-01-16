package com.example.orderservice.dto;

import lombok.Data;

@Data
public class RequestOrderDto {
    private String productId;
    private Integer qty;
    private Integer unitPrice;
}
