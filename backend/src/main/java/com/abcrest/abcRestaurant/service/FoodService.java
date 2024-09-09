package com.abcrest.abcRestaurant.service;

import com.abcrest.abcRestaurant.model.Category;
import com.abcrest.abcRestaurant.model.Food;
import com.abcrest.abcRestaurant.model.Restaurant;
import com.abcrest.abcRestaurant.request.CreateFoodRequest;

import java.util.List;

public interface FoodService {

    public Food createFood(CreateFoodRequest req, Category category, Restaurant restaurant);

    void deleteFood(String foodId) throws Exception;  // String for MongoDB ObjectId

    public List<Food> getRestaurantsFoods(String restaurantId,  // String for MongoDB ObjectId
                                          boolean isVegetarian,
                                          boolean isNonVeg,
                                          boolean isSeasonal,
                                          String foodCategory
    );

    public List<Food> searchFood(String keyword);

    public Food findFoodById(String foodId) throws Exception;  // String for MongoDB ObjectId

    public Food updateAvailabilityStatus(String foodId) throws Exception;  // String for MongoDB ObjectId
}
