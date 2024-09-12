package com.abcrest.abcRestaurant.repository;

import com.abcrest.abcRestaurant.model.Food;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import java.util.List;

public interface FoodRepository extends MongoRepository<Food, String> {

    // Search for food items based on name or description
    @Query("{ $or: [ { 'item_name': { $regex: ?0, $options: 'i' } }, { 'description': { $regex: ?0, $options: 'i' } } ] }")
    List<Food> searchFood(String keyword);
}
