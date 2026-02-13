//package com.adtech.ordermanagement.service;
//
//import com.adtech.ordermanagement.entity.OrderItem;
//import com.adtech.ordermanagement.repository.OrderItemRepository;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Service;
//import org.springframework.transaction.annotation.Transactional;
//
//import com.adtech.ordermanagement.entity.Order;
//import com.adtech.ordermanagement.entity.Product;
//import com.adtech.ordermanagement.repository.OrderRepository;
//import com.adtech.ordermanagement.repository.ProductRepository;
//import com.adtech.ordermanagement.dto.OrderRequest;
//import com.adtech.ordermanagement.dto.OrderItemRequest;
//
//import java.util.ArrayList;
//import java.util.List;
//
//@Service
//public class OrderService {
//
//    @Autowired
//    private ProductRepository productRepository;
//
//    @Autowired
//    private OrderRepository orderRepository;
//
//    @Transactional
//    public Order placeOrder(OrderRequest request) {
//
//        Order order = new Order();
//        double totalAmount = 0;
//
//        List<OrderItem> orderItems = new ArrayList<>();
//
//        for (OrderItemRequest itemRequest : request.getItems()) {
//
//            Product product = productRepository.findById(itemRequest.getProductId())
//                    .orElseThrow(() -> new RuntimeException("Product not found"));
//
//            if (product.getStock() < itemRequest.getQuantity()) {
//                throw new RuntimeException("Not enough stock for " + product.getName());
//            }
//
//            // reduce stock
//            product.setStock(product.getStock() - itemRequest.getQuantity());
//
//            OrderItem orderItem = new OrderItem();
//            orderItem.setProduct(product);
//            orderItem.setQuantity(itemRequest.getQuantity());
//            orderItem.setPrice(product.getPrice() * itemRequest.getQuantity());
//            orderItem.setOrder(order);
//
//
//            totalAmount += orderItem.getPrice();
//
//            orderItems.add(orderItem);
//        }
//
//        order.setItems(orderItems);
//        order.setTotalAmount(totalAmount);
//
//        return orderRepository.save(order);
//    }
//    public List<Order> getAllOrders() {
//        return orderRepository.findAll();
//    }
//    public List<Order> deleteOrder(Long id) {
//        return orderRepository.findAll();
//    }
//
//}
//package com.adtech.ordermanagement.service;
//
//import com.adtech.ordermanagement.dto.OrderItemResponse;
//import com.adtech.ordermanagement.dto.OrderResponse;
//import com.adtech.ordermanagement.entity.Order;
//import com.adtech.ordermanagement.entity.OrderItem;
//import com.adtech.ordermanagement.entity.Product;
//import com.adtech.ordermanagement.repository.OrderRepository;
//import com.adtech.ordermanagement.repository.ProductRepository;
//import com.adtech.ordermanagement.dto.OrderRequest;
//import com.adtech.ordermanagement.dto.OrderItemRequest;
//
//import org.springframework.stereotype.Service;
//import org.springframework.transaction.annotation.Transactional;
//
//import java.util.ArrayList;
//import java.util.List;
//
//@Service
//public class OrderService {
//
//    private final ProductRepository productRepository;
//    private final OrderRepository orderRepository;
//
//    public OrderService(ProductRepository productRepository,
//                        OrderRepository orderRepository) {
//        this.productRepository = productRepository;
//        this.orderRepository = orderRepository;
//    }
//
//    // ✅ PLACE ORDER
//    @Transactional
//    public OrderResponse placeOrder(OrderRequest request) {
//
//        Order order = new Order();
//        double totalAmount = 0;
//
//        List<OrderItem> orderItems = new ArrayList<>();
//
//        for (OrderItemRequest itemRequest : request.getItems()) {
//
//            Product product = productRepository.findById(itemRequest.getProductId())
//                    .orElseThrow(() -> new RuntimeException("Product not found"));
//
//            if (product.getStock() < itemRequest.getQuantity()) {
//                throw new RuntimeException("Not enough stock for " + product.getName());
//            }
//
//            product.setStock(product.getStock() - itemRequest.getQuantity());
//
//            OrderItem orderItem = new OrderItem();
//            orderItem.setProduct(product);
//            orderItem.setQuantity(itemRequest.getQuantity());
//            orderItem.setPrice(product.getPrice() * itemRequest.getQuantity());
//            orderItem.setOrder(order);
//
//            totalAmount += orderItem.getPrice();
//            orderItems.add(orderItem);
//        }
//
//        order.setItems(orderItems);
//        order.setTotalAmount(totalAmount);
//
//        // 🔥 ADD HERE
//        Order savedOrder = orderRepository.save(order);
//
//        List<OrderItemResponse> itemResponses = savedOrder.getItems().stream()
//                .map(item -> new OrderItemResponse(
//                        item.getProduct().getName(),
//                        item.getQuantity(),
//                        item.getPrice()
//                ))
//                .toList();
//
//        return new OrderResponse(
//                savedOrder.getId(),
//                savedOrder.getTotalAmount(),
//                savedOrder.getOrderDate(),
//                itemResponses
//        );
//    }
//
//    // ✅ GET ALL ORDERS
//    public List<Order> getAllOrders() {
//        return orderRepository.findAll();
//    }
//
//    // ✅ DELETE ORDER (with stock restore)
//    @Transactional
//    public void deleteOrder(Long id) {
//
//        Order order = orderRepository.findById(id)
//                .orElseThrow(() -> new RuntimeException("Order not found"));
//
//        // Restore stock before deleting
//        for (OrderItem item : order.getItems()) {
//            Product product = item.getProduct();
//            product.setStock(product.getStock() + item.getQuantity());
//        }
//
//        orderRepository.delete(order);
//    }
//}
package com.adtech.ordermanagement.service;

import com.adtech.ordermanagement.dto.OrderItemRequest;
import com.adtech.ordermanagement.dto.OrderItemResponse;
import com.adtech.ordermanagement.dto.OrderRequest;
import com.adtech.ordermanagement.dto.OrderResponse;
import com.adtech.ordermanagement.entity.Order;
import com.adtech.ordermanagement.entity.OrderItem;
import com.adtech.ordermanagement.entity.Product;
import com.adtech.ordermanagement.entity.User;
import com.adtech.ordermanagement.repository.OrderRepository;
import com.adtech.ordermanagement.repository.ProductRepository;
import com.adtech.ordermanagement.repository.UserRepository;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
public class OrderService {

    private final ProductRepository productRepository;
    private final OrderRepository orderRepository;
    private final UserRepository userRepository;

    public OrderService(ProductRepository productRepository,
                        OrderRepository orderRepository,
                        UserRepository userRepository) {
        this.productRepository = productRepository;
        this.orderRepository = orderRepository;
        this.userRepository = userRepository;
    }

    // ✅ PLACE ORDER
    @Transactional
    public OrderResponse placeOrder(OrderRequest request) {

        // 🔥 Get logged-in user
        Authentication authentication =
                SecurityContextHolder.getContext().getAuthentication();

        String username = authentication.getName();

        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("User not found"));

        Order order = new Order();
        order.setUser(user);   // 🔥 LINK ORDER TO USER

        double totalAmount = 0;
        List<OrderItem> orderItems = new ArrayList<>();

        for (OrderItemRequest itemRequest : request.getItems()) {

            Product product = productRepository.findById(itemRequest.getProductId())
                    .orElseThrow(() -> new RuntimeException("Product not found"));

            if (product.getStock() < itemRequest.getQuantity()) {
                throw new RuntimeException("Not enough stock for " + product.getName());
            }

            // Reduce stock
            product.setStock(product.getStock() - itemRequest.getQuantity());

            OrderItem orderItem = new OrderItem();
            orderItem.setProduct(product);
            orderItem.setQuantity(itemRequest.getQuantity());
            orderItem.setPrice(product.getPrice() * itemRequest.getQuantity());
            orderItem.setOrder(order);

            totalAmount += orderItem.getPrice();
            orderItems.add(orderItem);
        }

        order.setItems(orderItems);
        order.setTotalAmount(totalAmount);

        Order savedOrder = orderRepository.save(order);

        List<OrderItemResponse> itemResponses = savedOrder.getItems().stream()
                .map(item -> new OrderItemResponse(
                        item.getProduct().getName(),
                        item.getQuantity(),
                        item.getPrice()
                ))
                .toList();

        return new OrderResponse(
                savedOrder.getId(),
                savedOrder.getTotalAmount(),
                savedOrder.getOrderDate(),
                itemResponses
        );
    }

    // ✅ GET ALL ORDERS (Admin)
    public List<Order> getAllOrders() {
        return orderRepository.findAll();
    }

    // ✅ GET MY ORDERS (User only)
    public List<Order> getMyOrders() {

        String username = SecurityContextHolder
                .getContext()
                .getAuthentication()
                .getName();

        return orderRepository.findByUser_Username(username);
    }

    // ✅ DELETE ORDER (with stock restore)
    @Transactional
    public void deleteOrder(Long id) {

        Order order = orderRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Order not found"));

        // Restore stock before deleting
        for (OrderItem item : order.getItems()) {
            Product product = item.getProduct();
            product.setStock(product.getStock() + item.getQuantity());
        }

        orderRepository.delete(order);
    }
}
