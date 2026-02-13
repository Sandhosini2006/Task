package com.adtech.ordermanagement.repository;

import com.adtech.ordermanagement.entity.Order;
import com.adtech.ordermanagement.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {

    Optional<User> findByUsername(String username);

}
