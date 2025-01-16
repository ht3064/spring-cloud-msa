package com.example.orderservice.application;

import com.example.orderservice.domain.Order;
import com.example.orderservice.dto.OrderDto;

public interface OrderService {
    OrderDto createOrder(OrderDto orderDetails);
    OrderDto getOrderByOrderId(String orderId);
    Iterable<Order> getOrdersByMemberId(String memberId);
}
