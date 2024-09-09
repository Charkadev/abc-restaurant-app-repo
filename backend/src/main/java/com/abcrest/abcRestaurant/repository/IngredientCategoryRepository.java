package com.abcrest.abcRestaurant.repository;

import com.abcrest.abcRestaurant.model.IngredientCategory;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface IngredientCategoryRepository extends MongoRepository<IngredientCategory, String> {  // Use MongoRepository with String for MongoDB ObjectId

    List<IngredientCategory> findByRestaurantId(String restaurantId);  // String for MongoDB ObjectId
}
