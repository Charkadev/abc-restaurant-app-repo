package com.abcrest.abcRestaurant.controller;

import com.abcrest.abcRestaurant.model.Cart;
import com.abcrest.abcRestaurant.model.User;
import com.abcrest.abcRestaurant.model.USER_ROLE;
import com.abcrest.abcRestaurant.repository.CartRepository;
import com.abcrest.abcRestaurant.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/admin")
public class AdminController {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private CartRepository cartRepository;

    // Create a new user with any role
    @PostMapping("/users")
    public ResponseEntity<User> createUser(@RequestBody User user) {
        // Hash the password before saving
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        User savedUser = userRepository.save(user);

        // If the created user is a customer, create a cart for them
        if (user.getRole() == USER_ROLE.ROLE_CUSTOMER) {
            Cart cart = new Cart();
            cart.setUserId(savedUser.getId());
            cartRepository.save(cart);
        }

        return new ResponseEntity<>(savedUser, HttpStatus.CREATED);
    }

    // Update user details by ID
    @PutMapping("/users/{id}")
    public ResponseEntity<User> updateUser(@PathVariable String id, @RequestBody User user) {
        return userRepository.findById(id).map(existingUser -> {
            existingUser.setFullName(user.getFullName());
            existingUser.setEmail(user.getEmail());
            existingUser.setRole(user.getRole());

            // Update the password only if a new one is provided
            if (user.getPassword() != null && !user.getPassword().isEmpty()) {
                existingUser.setPassword(passwordEncoder.encode(user.getPassword()));
            }

            User updatedUser = userRepository.save(existingUser);

            // If the user role is changed to customer and they don't have a cart, create one
            if (existingUser.getRole() == USER_ROLE.ROLE_CUSTOMER && !cartRepository.findByUserId(updatedUser.getId()).isPresent()) {
                Cart cart = new Cart();
                cart.setUserId(updatedUser.getId());
                cartRepository.save(cart);
            }

            return new ResponseEntity<>(updatedUser, HttpStatus.OK);
        }).orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    // Delete a user by ID
    @DeleteMapping("/users/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable String id) {
        return userRepository.findById(id).map(user -> {
            userRepository.delete(user);
            return new ResponseEntity<Void>(HttpStatus.NO_CONTENT);  // Specify Void explicitly here
        }).orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    // Get all users
    @GetMapping("/users")
    public ResponseEntity<List<User>> getAllUsers() {
        List<User> users = userRepository.findAll();
        return new ResponseEntity<>(users, HttpStatus.OK);
    }

    // Get a single user by ID
    @GetMapping("/users/{id}")
    public ResponseEntity<User> getUserById(@PathVariable String id) {
        return userRepository.findById(id).map(user ->
                new ResponseEntity<>(user, HttpStatus.OK)
        ).orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }
}
