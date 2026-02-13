package com.adtech.ordermanagement.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.adtech.ordermanagement.entity.OrderItem;

public interface OrderItemRepository extends JpaRepository<OrderItem, Long> {

    boolean existsByProduct_Id(Long productId);
}
