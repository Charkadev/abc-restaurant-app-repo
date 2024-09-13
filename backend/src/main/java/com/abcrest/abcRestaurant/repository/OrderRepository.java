package com.abcrest.abcRestaurant.repository;

import com.abcrest.abcRestaurant.model.Order;
import org.springframework.data.mongodb.repository.MongoRepository;
import java.util.List;

public interface OrderRepository extends MongoRepository<Order, String> {

    // Find orders by the user's MongoDB ObjectId (String)
    List<Order> findByUserId(String userId);  // Matching the 'userId' field in the Order model
}
