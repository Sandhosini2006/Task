package com.adtech.ordermanagement.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.adtech.ordermanagement.entity.Product;

public interface ProductRepository extends JpaRepository<Product, Long> {
}
