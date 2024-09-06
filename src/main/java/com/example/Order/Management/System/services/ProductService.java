package com.example.Order.Management.System.services;

import com.example.Order.Management.System.model.Product;
import com.example.Order.Management.System.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductService {
    @Autowired
    private ProductRepository productRepository;

    @Cacheable("Allproducts")
    public List<Product> getAllProducts() {
        return productRepository.findAll();
    }

    public List<Product> searchProducts(String name) {
        return productRepository.findByNameContaining(name);
    }

    public Product createProduct(Product product) {
        if (product.getPrice() == null) {
            throw new IllegalArgumentException("Price cannot be null");
        }
        productRepository.save(product);
        return product;
    }

    public boolean deleteProduct(Integer id) {
        try {
        productRepository.deleteById(id);
        return true;
        }catch (Exception exception){
            return false;
        }
    }
    public List<Product> searchById(List<Integer>Ids){
        return productRepository.findAllById(Ids);
    }
}
