package com.abcrest.abcRestaurant.repository;

import com.abcrest.abcRestaurant.model.Reservation;
import org.springframework.data.mongodb.repository.MongoRepository;
import java.util.List;

public interface ReservationRepository extends MongoRepository<Reservation, String> {
    List<Reservation> findByCustomerId(String customerId);  // Find reservations by customer ID
}
