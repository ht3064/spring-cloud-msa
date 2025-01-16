package com.example.orderservice.dao;

import com.example.orderservice.domain.Order;
import org.springframework.data.repository.CrudRepository;

public interface OrderRepository extends CrudRepository<Order, Long> {
    Order findByOrderId(String orderId);

    Iterable<Order> findByMemberId(String memberId);
}
