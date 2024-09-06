package com.example.Order.Management.System.Controllers;

import com.example.Order.Management.System.model.Product;
import com.example.Order.Management.System.services.ProductService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.Min;
import java.util.List;

@RestController
@RequestMapping("/api/products")
@Validated
public class ProductController {

    private static final Logger logger = LoggerFactory.getLogger(ProductController.class);

    @Autowired
    private ProductService productService;

    @GetMapping
    public ResponseEntity<List<Product>> getAllProducts() {
        logger.info("Fetching all products");
        List<Product> products = productService.getAllProducts();
        return ResponseEntity.ok(products);
    }

    @GetMapping("/search")
    public ResponseEntity<List<Product>> searchProducts(@RequestParam String name) {
        logger.info("Searching products with name: {}", name);
        List<Product> products = productService.searchProducts(name);
        if (products.isEmpty()) {
            logger.info("No products found for name: {}", name);
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(products);
    }

    @PostMapping
    public ResponseEntity<Product> createProduct(@Valid @RequestBody Product product) {
        logger.info("Creating product: {}", product);
        Product createdProduct = productService.createProduct(product);
        return new ResponseEntity<>(createdProduct, HttpStatus.CREATED);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteProduct(@PathVariable @Min(1) Integer id) {
        logger.info("Deleting product with ID: {}", id);
        if (productService.deleteProduct(id)) {
            logger.info("Product with ID: {} deleted successfully", id);
            return ResponseEntity.noContent().build();
        } else {
            logger.warn("Product with ID: {} not found", id);
            return ResponseEntity.notFound().build();
        }
    }
}
