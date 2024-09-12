package com.abcrest.abcRestaurant.service;

import com.abcrest.abcRestaurant.dto.RestaurantDto;
import com.abcrest.abcRestaurant.model.Restaurant;
import com.abcrest.abcRestaurant.model.User;
import com.abcrest.abcRestaurant.request.CreateRestaurantRequest;

import java.util.List;

public interface RestaurantService {

    Restaurant createRestaurant(CreateRestaurantRequest req, User user);

    Restaurant updateRestaurant(String restaurantId, CreateRestaurantRequest updatedRestaurant) throws Exception;

    void deleteRestaurant(String restaurantId) throws Exception;

    List<Restaurant> getAllRestaurant();

    List<Restaurant> searchRestaurant(String keyword);

    Restaurant findRestaurantById(String id) throws Exception;

    Restaurant findRestaurantByUserId(String userId) throws Exception;

    Restaurant updateRestaurantStatus(String restaurantId) throws Exception;


}
