package com.abcrest.abcRestaurant.service;

import com.abcrest.abcRestaurant.dto.RestaurantDto;
import com.abcrest.abcRestaurant.model.Restaurant;
import com.abcrest.abcRestaurant.model.User;
import com.abcrest.abcRestaurant.request.CreateRestaurantRequest;

import java.util.List;

public interface RestaurantService {

    // Create a new restaurant
    Restaurant createRestaurant(CreateRestaurantRequest req, User user);

    // Update an existing restaurant by String restaurantId (MongoDB ObjectId)
    Restaurant updateRestaurant(String restaurantId, CreateRestaurantRequest updatedRestaurant) throws Exception;

    // Delete a restaurant by String restaurantId (MongoDB ObjectId)
    void deleteRestaurant(String restaurantId) throws Exception;

    // Retrieve all restaurants
    List<Restaurant> getAllRestaurant();

    // Search for restaurants based on a keyword
    List<Restaurant> searchRestaurant(String keyword);

    // Find a restaurant by String id (MongoDB ObjectId)
    Restaurant findRestaurantById(String id) throws Exception;

    // Find a restaurant by the owner's String userId (MongoDB ObjectId)
    Restaurant findRestaurantByUserId(String userId) throws Exception;

    // Add a restaurant to the user's favorites
    RestaurantDto addToFavorites(String restaurantId, User user) throws Exception;

    // Update the status (e.g., open/closed) of a restaurant
    Restaurant updateRestaurantStatus(String restaurantId) throws Exception;
}
