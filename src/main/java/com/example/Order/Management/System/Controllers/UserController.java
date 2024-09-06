package com.example.Order.Management.System.Controllers;

import com.example.Order.Management.System.Exeptions.UserNotFoundException;
import com.example.Order.Management.System.model.User;
import com.example.Order.Management.System.services.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/api/users")
@Validated
public class UserController {

    private static final Logger logger = LoggerFactory.getLogger(UserController.class);

    @Autowired
    private UserService userService;

    @PostMapping
    public ResponseEntity<User> createUser(@Valid @RequestBody User user) {
        logger.info("Creating new user with username: {}", user.getUsername());
        User createdUser = userService.createUser(user);
        logger.info("User created with username: {}", createdUser.getUsername());
        return new ResponseEntity<>(createdUser, HttpStatus.CREATED);
    }

    @GetMapping("/{username}")
    public ResponseEntity<User> getUserByUsername(@PathVariable String username) {
        logger.info("Fetching user with username: {}", username);
        User user = userService.findByUsername(username)
                .orElseThrow(() -> new UserNotFoundException("User not found with username: " + username));
        logger.info("User found with username: {}", username);
        return ResponseEntity.ok(user);
    }
}
