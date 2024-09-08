package com.abcrest.abcRestaurant.repository;

import com.abcrest.abcRestaurant.model.User;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface UserRepository extends MongoRepository<User, String> {  // MongoRepository, and String for MongoDB ObjectId

    // Custom query to find a user by email
    public User findByEmail(String email);
}
