package com.abcrest.abcRestaurant.repository;

import com.abcrest.abcRestaurant.model.Restaurant;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import java.util.List;

public interface RestaurantRepository extends MongoRepository<Restaurant, String> {  // Use MongoRepository with String for ObjectId

    // MongoDB query syntax using regex for search
    @Query("{ '$or': [ { 'name': { '$regex': ?0, '$options': 'i' } }, { 'cuisineType': { '$regex': ?0, '$options': 'i' } } ] }")
    List<Restaurant> findBySearchQuery(String query);

    // Find a restaurant by the owner's ID (String for MongoDB ObjectId)
    Restaurant findByOwnerId(String ownerId);
}
