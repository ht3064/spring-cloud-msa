package com.example.orderservice.application;

import com.example.orderservice.dao.OrderRepository;
import com.example.orderservice.domain.Order;
import com.example.orderservice.dto.OrderDto;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService {
    private final OrderRepository orderRepository;

    @Override
    public OrderDto createOrder(OrderDto orderDto) {
        orderDto.setOrderId(UUID.randomUUID().toString());
        orderDto.setTotalPrice(orderDto.getQty() * orderDto.getUnitPrice());

        ModelMapper mapper = new ModelMapper();
        mapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
        Order order = mapper.map(orderDto, Order.class);

        orderRepository.save(order);

        OrderDto returnValue = mapper.map(order, OrderDto.class);

        return returnValue;
    }

    @Override
    public OrderDto getOrderByOrderId(String orderId) {
        Order order = orderRepository.findByOrderId(orderId);

        return new ModelMapper().map(order, OrderDto.class);
    }

    @Override
    public Iterable<Order> getOrdersByMemberId(String memberId) {
        return orderRepository.findByMemberId(memberId);
    }
}
