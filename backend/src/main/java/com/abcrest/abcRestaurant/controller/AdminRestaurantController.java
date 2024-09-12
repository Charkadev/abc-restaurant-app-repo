package com.abcrest.abcRestaurant.controller;

import com.abcrest.abcRestaurant.model.Restaurant;
import com.abcrest.abcRestaurant.model.User;
import com.abcrest.abcRestaurant.request.CreateRestaurantRequest;
import com.abcrest.abcRestaurant.response.MessageResponse;
import com.abcrest.abcRestaurant.service.RestaurantService;
import com.abcrest.abcRestaurant.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/staff/restaurants")  // Shared route for both Admins and Staff
public class AdminRestaurantController {

    @Autowired
    private RestaurantService restaurantService;

    @Autowired
    private UserService userService;

    // GET method to fetch all restaurants (accessible by Admin and Staff)
    @PreAuthorize("hasAnyRole('ADMIN', 'RESTAURANT_STAFF')")
    @GetMapping()
    public ResponseEntity<List<Restaurant>> getAllRestaurants(
            @RequestHeader("Authorization") String jwt
    ) throws Exception {
        List<Restaurant> restaurants = restaurantService.getAllRestaurant();
        return new ResponseEntity<>(restaurants, HttpStatus.OK);
    }

    // Only Admins can create a new restaurant
    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping()
    public ResponseEntity<Restaurant> createRestaurant(
            @RequestBody CreateRestaurantRequest req,
            @RequestHeader("Authorization") String jwt
    ) throws Exception {
        User user = userService.findUserByJwtToken(jwt);
        Restaurant restaurant = restaurantService.createRestaurant(req, user);
        return new ResponseEntity<>(restaurant, HttpStatus.CREATED);
    }

    // Both Admin and Restaurant Staff can update a restaurant
    @PreAuthorize("hasAnyRole('ADMIN', 'RESTAURANT_STAFF')")
    @PutMapping("/{id}")
    public ResponseEntity<Restaurant> updateRestaurant(
            @RequestBody CreateRestaurantRequest req,
            @RequestHeader("Authorization") String jwt,
            @PathVariable String id
    ) throws Exception {
        User user = userService.findUserByJwtToken(jwt);
        Restaurant restaurant = restaurantService.updateRestaurant(id, req);
        return new ResponseEntity<>(restaurant, HttpStatus.OK);
    }

    // Only Admins can delete a restaurant
    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/{id}")
    public ResponseEntity<MessageResponse> deleteRestaurant(
            @RequestHeader("Authorization") String jwt,
            @PathVariable String id
    ) throws Exception {
        restaurantService.deleteRestaurant(id);
        MessageResponse res = new MessageResponse();
        res.setMessage("Restaurant deleted successfully");
        return new ResponseEntity<>(res, HttpStatus.OK);
    }

    // Both Admin and Restaurant Staff can update restaurant status (open/closed)
    @PreAuthorize("hasAnyRole('ADMIN', 'RESTAURANT_STAFF')")
    @PutMapping("/{id}/status")
    public ResponseEntity<Restaurant> updateRestaurantStatus(
            @RequestHeader("Authorization") String jwt,
            @PathVariable String id
    ) throws Exception {
        Restaurant restaurant = restaurantService.updateRestaurantStatus(id);
        return new ResponseEntity<>(restaurant, HttpStatus.OK);
    }

    // Both Admin and Restaurant Staff can find a restaurant by user ID
    @PreAuthorize("hasAnyRole('ADMIN', 'RESTAURANT_STAFF')")
    @GetMapping("/user")
    public ResponseEntity<Restaurant> findRestaurantByUserId(
            @RequestHeader("Authorization") String jwt
    ) throws Exception {
        User user = userService.findUserByJwtToken(jwt);
        Restaurant restaurant = restaurantService.findRestaurantByUserId(user.getId());
        return new ResponseEntity<>(restaurant, HttpStatus.OK);
    }
}
