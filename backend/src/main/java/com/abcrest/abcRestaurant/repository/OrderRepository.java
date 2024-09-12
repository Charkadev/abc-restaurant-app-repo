package com.abcrest.abcRestaurant.repository;

import com.abcrest.abcRestaurant.model.Order;
import org.springframework.data.mongodb.repository.MongoRepository;
import java.util.List;

public interface OrderRepository extends MongoRepository<Order, String> {

    // Find orders by the customer's MongoDB ObjectId (String)
    List<Order> findByCustomerId(String userId);

    // Removed: List<Order> findByRestaurantId(String restaurantId);
}
