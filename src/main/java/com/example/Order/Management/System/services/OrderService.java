package com.example.Order.Management.System.services;

import com.example.Order.Management.System.model.Order;
import com.example.Order.Management.System.model.Product;
import com.example.Order.Management.System.model.User;
import com.example.Order.Management.System.repository.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OrderService {

    @Autowired
    private OrderRepository orderRepository;

    public List<Order> getOrdersByUser(User user) {
        return orderRepository.findByUser(user);
    }

    public Order createOrder(User user, List<Product> products) {
        if (products == null || products.isEmpty()) {
            throw new IllegalArgumentException("Product list cannot be null or empty");
        }
        Order order = new Order();
        order.setUser(user);
        order.setProducts(products);
//        order.calculateTotalPrice();
        return orderRepository.save(order);
    }


}
