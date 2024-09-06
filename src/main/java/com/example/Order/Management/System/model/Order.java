package com.example.Order.Management.System.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.Objects;


@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "orders")
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;


    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    @JsonBackReference
    private User user;

    @ManyToMany
    @JoinTable(
            name = "order_products",
            joinColumns = @JoinColumn(name = "order_id"),
            inverseJoinColumns = @JoinColumn(name = "product_id")
    )
    private List<Product> products;


    @Column(name = "total_price", nullable = false)
    private Double totalPrice;


    public Order(User user, List<Product> products, Double totalPrice) {
        this.user = user;
        this.products = products;
        this.totalPrice = totalPrice;
    }

    @PrePersist
    @PreUpdate
    public void calculateTotalPrice() {
        this.totalPrice = products.stream().filter(Objects::nonNull)
                .mapToDouble(Product::getPrice)
                .sum();
    }
}
