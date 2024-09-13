package com.abcrest.abcRestaurant.request;

import lombok.Data;

@Data  // Lombok will generate getters, setters, toString, equals, and hashCode
public class PaymentRequest {
    private String paymentMethod;
    private String cardNumber;
    private String expiryDate;
    private String cvv;
}
