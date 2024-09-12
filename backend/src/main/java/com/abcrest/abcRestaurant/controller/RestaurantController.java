package com.abcrest.abcRestaurant.controller;

import com.abcrest.abcRestaurant.dto.RestaurantDto;
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

    // Fetch restaurant by ID (public)
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

    // Add a restaurant to user's favorites (requires authorization)
    @PutMapping("/{id}/add-favorites")
    public ResponseEntity<RestaurantDto> addToFavorites(
            @RequestHeader("Authorization") String jwt, @PathVariable String id) throws Exception {
        // Assuming you retrieve the user by JWT token
        // User user = userService.findUserByJwtToken(jwt);  // Uncomment if user retrieval is necessary
        RestaurantDto restaurant = restaurantService.addToFavorites(id, null); // Replace `null` with `user` when userService is added
        return new ResponseEntity<>(restaurant, HttpStatus.OK);
    }
}
