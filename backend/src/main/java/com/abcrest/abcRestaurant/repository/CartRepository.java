package com.abcrest.abcRestaurant.repository;

import com.abcrest.abcRestaurant.model.Cart;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface CartRepository extends MongoRepository<Cart, String> {
    Cart findByCustomerId(String userId);  // String for MongoDB ObjectId
}
