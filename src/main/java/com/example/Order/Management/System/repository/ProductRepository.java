package com.example.Order.Management.System.repository;

import com.example.Order.Management.System.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProductRepository extends JpaRepository<Product,Integer> {
    List<Product> findByNameContaining(String name);
}
