//package com.adtech.ordermanagement.repository;
//
//import com.adtech.ordermanagement.entity.Order;
//import org.springframework.data.jpa.repository.JpaRepository;
//
//public interface OrderRepository extends JpaRepository<Order, Long> {
//
//    boolean existsByProduct_Id(Long productId);
//
//}
package com.adtech.ordermanagement.repository;

import com.adtech.ordermanagement.entity.Order;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface OrderRepository extends JpaRepository<Order, Long> {

    List<Order> findByUser_Username(String username);


}
