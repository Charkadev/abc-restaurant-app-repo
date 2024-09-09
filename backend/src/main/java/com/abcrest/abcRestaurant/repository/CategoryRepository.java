package com.abcrest.abcRestaurant.repository;

import com.abcrest.abcRestaurant.model.Category;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface CategoryRepository extends MongoRepository<Category, String> {  // Use MongoRepository with String for MongoDB ObjectId

    public List<Category> findByRestaurantId(String restaurantId);  // String for MongoDB ObjectId
}
