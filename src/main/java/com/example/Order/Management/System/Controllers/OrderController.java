package com.example.Order.Management.System.Controllers;

import com.example.Order.Management.System.Exeptions.UserNotFoundException;
import com.example.Order.Management.System.model.Order;
import com.example.Order.Management.System.model.Product;
import com.example.Order.Management.System.model.User;
import com.example.Order.Management.System.services.OrderService;
import com.example.Order.Management.System.services.ProductService;
import com.example.Order.Management.System.services.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/orders")
public class OrderController {

    private static final Logger logger = LoggerFactory.getLogger(OrderController.class);

    @Autowired
    private OrderService orderService;

    @Autowired
    private UserService userService;

    @Autowired
    private ProductService productService;

    @GetMapping("/user/{username}")
    public ResponseEntity<List<Order>> getOrdersByUser(@PathVariable String username) {
        logger.info("Fetching orders for user: {}", username);
        User user = userService.findByUsername(username)
                .orElseThrow(() -> new UserNotFoundException("User not found with username: " + username));
        List<Order> orders = orderService.getOrdersByUser(user);
        logger.info("Found {} orders for user: {}", orders.size(), username);
        return new ResponseEntity<>(orders, HttpStatus.OK);
    }

    @PostMapping("/user/{username}/products")
    public ResponseEntity<Order> createOrderWithProductIds(@PathVariable String username, @RequestBody List<Integer> productIds) {
        logger.info("Creating order for user: {} with product IDs: {}", username, productIds);
        User user = userService.findByUsername(username)
                .orElseThrow(() -> new UserNotFoundException("User not found with username: " + username));
        List<Product> products = productService.searchById(productIds);
        Order order = orderService.createOrder(user, products);
        logger.info("Order created successfully for user: {} with order ID: {}", username, order.getId());
        return new ResponseEntity<>(order, HttpStatus.CREATED);
    }

    @PostMapping("/user/{username}/products/full")
    public ResponseEntity<Order> createOrderWithProducts(@PathVariable String username, @RequestBody List<Product> products) {
        logger.info("Creating order for user: {} with products: {}", username, products);
        User user = userService.findByUsername(username)
                .orElseThrow(() -> new UserNotFoundException("User not found with username: " + username));
        Order order = orderService.createOrder(user, products);
        logger.info("Order created successfully for user: {} with order ID: {}", username, order.getId());
        return new ResponseEntity<>(order, HttpStatus.CREATED);
    }
}
