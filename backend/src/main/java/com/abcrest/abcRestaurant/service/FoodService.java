package com.abcrest.abcRestaurant.service;

import com.abcrest.abcRestaurant.model.Food;
import com.abcrest.abcRestaurant.request.CreateFoodRequest;

import java.util.List;

public interface FoodService {

    Food createFood(CreateFoodRequest req);

    void deleteFood(String foodId) throws Exception;

    List<Food> searchFood(String keyword);

    Food findFoodById(String foodId) throws Exception;

    Food updateAvailabilityStatus(String foodId) throws Exception;
}
