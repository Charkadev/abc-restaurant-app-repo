package com.abcrest.abcRestaurant.controller;

import com.abcrest.abcRestaurant.model.Order;
import com.abcrest.abcRestaurant.model.User;
import com.abcrest.abcRestaurant.service.OrderService;
import com.abcrest.abcRestaurant.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/admin")
public class AdminOrderController {

    @Autowired
    private OrderService orderService;

    @Autowired
    private UserService userService;

    /**
     * Get the order history for a specific restaurant by its ID
     * Only Admin or Restaurant Staff can access this
     */
    @PreAuthorize("hasAnyRole('ADMIN', 'RESTAURANT_STAFF')")  // Ensure only Admins or Staff can access
    @GetMapping("/order/restaurant/{id}")
    public ResponseEntity<List<Order>> getOrderHistory(
            @PathVariable String id,  // Changed to String for MongoDB ObjectId
            @RequestParam(required = false) String order_status,
            @RequestHeader("Authorization") String jwt) throws Exception {

        // Validate user role and find user by JWT token
        User user = userService.findUserByJwtToken(jwt);

        // Retrieve the order history for the restaurant by restaurant ID
        List<Order> orders = orderService.getRestaurantsOrder(id, order_status);
        return new ResponseEntity<>(orders, HttpStatus.OK);
    }

    /**
     * Update the status of a specific order
     * Only Admin or Restaurant Staff can update the order status
     */
    @PreAuthorize("hasAnyRole('ADMIN', 'RESTAURANT_STAFF')")  // Ensure only Admins or Staff can access
    @PutMapping("/order/{id}/{orderStatus}")
    public ResponseEntity<Order> updateOrderStatus(
            @PathVariable String id,  // Changed to String for MongoDB ObjectId
            @PathVariable String orderStatus,
            @RequestHeader("Authorization") String jwt) throws Exception {

        // Validate user role and find user by JWT token
        User user = userService.findUserByJwtToken(jwt);

        // Update the order status by its ID and the provided status
        Order orders = orderService.updateOrder(id, orderStatus);
        return new ResponseEntity<>(orders, HttpStatus.OK);
    }
}
