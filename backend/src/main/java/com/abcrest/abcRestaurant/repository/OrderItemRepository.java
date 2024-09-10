package com.abcrest.abcRestaurant.repository;

import com.abcrest.abcRestaurant.model.OrderItem;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface OrderItemRepository extends MongoRepository<OrderItem, String> {
}
