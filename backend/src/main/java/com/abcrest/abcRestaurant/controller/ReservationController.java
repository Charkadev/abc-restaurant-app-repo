package com.abcrest.abcRestaurant.controller;

import com.abcrest.abcRestaurant.model.Reservation;
import com.abcrest.abcRestaurant.model.User;
import com.abcrest.abcRestaurant.repository.ReservationRepository;
import com.abcrest.abcRestaurant.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("/api/reservations")
public class ReservationController {

    @Autowired
    private ReservationRepository reservationRepository;

    @Autowired
    private UserRepository userRepository;

    // Create a new reservation
    @PostMapping
    public ResponseEntity<?> createReservation(@RequestBody Reservation reservation, Authentication auth) {
        String email = auth.getName();
        User customer = userRepository.findByEmail(email);
        if (customer == null) {
            return new ResponseEntity<>("User not found", HttpStatus.NOT_FOUND);
        }

        reservation.setCustomer(customer);
        reservation.setStatus("PENDING");  // Set the initial status of the reservation
        reservationRepository.save(reservation);

        return new ResponseEntity<>("Reservation created successfully", HttpStatus.CREATED);
    }

    // Get all reservations (for staff/admins)
    @GetMapping("/all")
    public ResponseEntity<List<Reservation>> getAllReservations() {
        List<Reservation> reservations = reservationRepository.findAll();
        return new ResponseEntity<>(reservations, HttpStatus.OK);
    }

    // Get reservations by customer ID
    @GetMapping("/customer/{customerId}")
    public ResponseEntity<List<Reservation>> getReservationsByCustomer(@PathVariable String customerId) {
        List<Reservation> reservations = reservationRepository.findByCustomerId(customerId);
        return new ResponseEntity<>(reservations, HttpStatus.OK);
    }
}
