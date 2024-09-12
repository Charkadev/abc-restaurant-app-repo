package com.abcrest.abcRestaurant.controller;

import com.abcrest.abcRestaurant.model.Restaurant;
import com.abcrest.abcRestaurant.service.RestaurantService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/restaurants")
public class RestaurantController {

    @Autowired
    private RestaurantService restaurantService;

    // Public access to fetch all restaurants without requiring authorization
    @GetMapping()
    public ResponseEntity<List<Restaurant>> getAllRestaurant() {
        List<Restaurant> restaurants = restaurantService.getAllRestaurant();
        return new ResponseEntity<>(restaurants, HttpStatus.OK);
    }

    // Fetch restaurant by ID (public access)
    @GetMapping("/{id}")
    public ResponseEntity<Restaurant> findRestaurantById(@PathVariable String id) throws Exception {
        Restaurant restaurant = restaurantService.findRestaurantById(id);
        return new ResponseEntity<>(restaurant, HttpStatus.OK);
    }

    // Search restaurants by keyword (requires authorization)
    @GetMapping("/search")
    public ResponseEntity<List<Restaurant>> searchRestaurant(
            @RequestHeader("Authorization") String jwt, @RequestParam String keyword) throws Exception {
        // Assuming search requires authorization
        List<Restaurant> restaurants = restaurantService.searchRestaurant(keyword);
        return new ResponseEntity<>(restaurants, HttpStatus.OK);
    }

    // Removed add to favorites functionality as requested
}
