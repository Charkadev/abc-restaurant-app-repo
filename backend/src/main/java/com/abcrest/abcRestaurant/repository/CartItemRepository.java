package com.abcrest.abcRestaurant.repository;

import com.abcrest.abcRestaurant.model.CartItem;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface CartItemRepository extends MongoRepository<CartItem, String> {
    // No additional methods are required at this moment, using default CRUD provided by MongoRepository
}
