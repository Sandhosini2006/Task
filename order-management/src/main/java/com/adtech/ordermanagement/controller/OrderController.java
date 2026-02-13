package com.adtech.ordermanagement.controller;

import com.adtech.ordermanagement.dto.OrderRequest;
import com.adtech.ordermanagement.dto.OrderResponse;
import com.adtech.ordermanagement.entity.Order;
import com.adtech.ordermanagement.service.OrderService;

import org.apache.tomcat.util.net.openssl.ciphers.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/orders")
public class OrderController {

    private final OrderService orderService;

    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    // ✅ New multi-product place order
    @PostMapping("/place")
    public OrderResponse placeOrder(@RequestBody OrderRequest request) {
        return orderService.placeOrder(request);
    }

    @GetMapping
    public List<Order> getAllOrders() {
        return orderService.getAllOrders();
    }
    @GetMapping("/my")
    public List<Order> myOrders() {
        return orderService.getMyOrders();
    }


    @DeleteMapping("/{id}")
    public void deleteOrder(@PathVariable Long id) {
        orderService.deleteOrder(id);
    }
}
