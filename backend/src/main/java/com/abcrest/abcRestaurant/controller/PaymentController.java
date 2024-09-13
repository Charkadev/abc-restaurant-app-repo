package com.abcrest.abcRestaurant.controller;

import com.abcrest.abcRestaurant.request.PaymentRequest;
import com.abcrest.abcRestaurant.service.PaymentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
public class PaymentController {

    @Autowired
    private PaymentService paymentService;

    @PostMapping("/payment/{orderId}")
    public ResponseEntity<String> processPayment(@PathVariable String orderId,
                                                 @RequestBody PaymentRequest paymentRequest) {
        try {
            boolean paymentSuccess = paymentService.processPayment(orderId, paymentRequest);
            if (paymentSuccess) {
                return new ResponseEntity<>("Payment successful", HttpStatus.OK);
            } else {
                return new ResponseEntity<>("Payment failed", HttpStatus.BAD_REQUEST);
            }
        } catch (Exception e) {
            return new ResponseEntity<>("Payment error: " + e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }
}
