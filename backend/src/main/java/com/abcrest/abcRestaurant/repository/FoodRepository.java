package com.abcrest.abcRestaurant.repository;

import com.abcrest.abcRestaurant.model.Food;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import java.util.List;

public interface FoodRepository extends MongoRepository<Food, String> {  // MongoRepository with String for ObjectId

    List<Food> findByRestaurantId(String restaurantId);  // String for MongoDB ObjectId

    @Query("{ 'name': { $regex: ?0, $options: 'i' } }")
    List<Food> searchFood(String keyword);
}
