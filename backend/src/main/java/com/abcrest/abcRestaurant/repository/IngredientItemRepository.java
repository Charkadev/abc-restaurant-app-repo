package com.abcrest.abcRestaurant.repository;

import com.abcrest.abcRestaurant.model.IngredientsItem;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface IngredientItemRepository extends MongoRepository<IngredientsItem, String> {  // Use MongoRepository with String for MongoDB ObjectId

    List<IngredientsItem> findByRestaurantId(String restaurantId);  // String for MongoDB ObjectId
}
