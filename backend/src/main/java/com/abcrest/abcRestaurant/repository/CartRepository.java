package com.abcrest.abcRestaurant.repository;

import com.abcrest.abcRestaurant.model.Cart;
import org.springframework.data.mongodb.repository.MongoRepository;
import java.util.Optional;

public interface CartRepository extends MongoRepository<Cart, String> {
    Optional<Cart> findByUserId(String userId);  // Use Optional to handle cases where no cart is found
}
