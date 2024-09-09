package com.abcrest.abcRestaurant.repository;

import com.abcrest.abcRestaurant.model.Address;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface AddressRepository extends MongoRepository<Address, String> {  // Use MongoRepository and String for MongoDB ObjectId
}
