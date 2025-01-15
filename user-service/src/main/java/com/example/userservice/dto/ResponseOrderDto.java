package com.example.userservice.dto;

import lombok.Data;

import java.util.Date;

@Data
public class ResponseOrderDto {
    private String productId;
    private Integer qty;
    private Integer unitPrice;
    private Integer totalPrice;
    private Date createdAt;

    private String orderId;
}
