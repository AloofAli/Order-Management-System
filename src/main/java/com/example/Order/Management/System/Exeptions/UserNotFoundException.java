package com.example.Order.Management.System.Exeptions;

public class UserNotFoundException extends RuntimeException {
    public UserNotFoundException(String username) {
        super("User with username '" + username + "' not found.");
    }
}