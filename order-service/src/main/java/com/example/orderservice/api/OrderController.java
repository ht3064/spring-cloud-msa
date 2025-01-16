package com.example.orderservice.api;

import com.example.orderservice.application.OrderService;
import com.example.orderservice.domain.Order;
import com.example.orderservice.dto.OrderDto;
import com.example.orderservice.dto.RequestOrderDto;
import com.example.orderservice.dto.ResponseOrderDto;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/order-service")
public class OrderController {
    private final OrderService orderService;
    private final Environment env;

    @GetMapping("/health_check")
    public String status() {
        return String.format("It's Working in Order Service on PORT %S", env.getProperty("local.server.port"));
    }

    @PostMapping("/{memberId}/orders")
    public ResponseEntity<ResponseOrderDto> createOrder(@PathVariable("memberId") String memberId,
                                                        @RequestBody RequestOrderDto request) {
        ModelMapper mapper = new ModelMapper();
        mapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);

        OrderDto orderDto = mapper.map(request, OrderDto.class);
        orderDto.setMemberId(memberId);
        OrderDto createOrderDto = orderService.createOrder(orderDto);

        ResponseOrderDto responseOrderDto = mapper.map(createOrderDto, ResponseOrderDto.class);

        return ResponseEntity.status(HttpStatus.CREATED).body(responseOrderDto);
    }

    @GetMapping("/{memberId}/orders")
    public ResponseEntity<List<ResponseOrderDto>> getOrder(@PathVariable("memberId") String memberId) {
        Iterable<Order> orderList = orderService.getOrdersByMemberId(memberId);

        List<ResponseOrderDto> result = new ArrayList<>();
        orderList.forEach(v -> {
            result.add(new ModelMapper().map(v, ResponseOrderDto.class));
        });

        return ResponseEntity.status(HttpStatus.OK).body(result);
    }
}
