package com.abcrest.abcRestaurant.repository;

import com.abcrest.abcRestaurant.model.Food;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import java.util.List;

public interface FoodRepository extends MongoRepository<Food, String> {

    // Find foods by restaurant ID
    @Query("{ 'restaurant.id': ?0 }")
    List<Food> findByRestaurantId(String restaurantId);

    // Search for food items based on name or description
    @Query("{ $or: [ { 'name': { $regex: ?0, $options: 'i' } }, { 'description': { $regex: ?0, $options: 'i' } } ] }")
    List<Food> searchFood(String keyword);
}
