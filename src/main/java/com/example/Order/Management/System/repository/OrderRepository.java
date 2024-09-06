package com.example.Order.Management.System.repository;

import com.example.Order.Management.System.model.Order;
import com.example.Order.Management.System.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface OrderRepository extends JpaRepository<Order,Integer> {
    List<Order> findByUser(User user);
}
