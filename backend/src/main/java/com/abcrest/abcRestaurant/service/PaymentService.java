package com.abcrest.abcRestaurant.service;

import com.abcrest.abcRestaurant.request.PaymentRequest;

public interface PaymentService {
    boolean processPayment(String orderId, PaymentRequest paymentRequest) throws Exception;
}

