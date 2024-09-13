package com.abcrest.abcRestaurant.controller;

import com.abcrest.abcRestaurant.model.Order;
import com.abcrest.abcRestaurant.model.User;
import com.abcrest.abcRestaurant.request.OrderRequest;
import com.abcrest.abcRestaurant.service.OrderService;
import com.abcrest.abcRestaurant.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.List;

@RestController
@RequestMapping("/api")
public class OrderController {

    @Autowired
    private OrderService orderService;

    @Autowired
    private UserService userService;

    @PostMapping("/order")
    public ResponseEntity<Order> createOrder(@RequestBody OrderRequest req,
                                             @RequestHeader("Authorization") String jwt) throws Exception {
        User user = userService.findUserByJwtToken(jwt);
        Order order = orderService.createOrder(req, user);
        return new ResponseEntity<>(order, HttpStatus.OK);
    }

    @GetMapping("/order/user")
    public ResponseEntity<List<Order>> getOrderHistory(
            @RequestHeader("Authorization") String jwt) throws Exception {
        User user = userService.findUserByJwtToken(jwt);
        List<Order> orders = orderService.getUsersOrder(user.getId());
        return new ResponseEntity<>(orders, HttpStatus.OK);
    }

    // New endpoint for preparing the order before payment
    @PostMapping("/order/prepare")
    public ResponseEntity<?> prepareOrder(@RequestBody OrderRequest orderRequest, @RequestHeader("Authorization") String token) {
        try {
            User user = userService.findUserByJwtToken(token);
            Order order = orderService.createTemporaryOrder(user, orderRequest);
            return ResponseEntity.ok(Collections.singletonMap("orderId", order.getId()));
        } catch (Exception e) {
            return new ResponseEntity<>("Failed to prepare order: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
