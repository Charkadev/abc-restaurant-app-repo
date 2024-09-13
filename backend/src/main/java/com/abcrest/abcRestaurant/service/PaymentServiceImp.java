package com.abcrest.abcRestaurant.service;

import com.abcrest.abcRestaurant.model.Order;
import com.abcrest.abcRestaurant.repository.OrderRepository;
import com.abcrest.abcRestaurant.request.PaymentRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class PaymentServiceImp implements PaymentService {

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private OrderService orderService;

    @Override
    public boolean processPayment(String orderId, PaymentRequest paymentRequest) {
        // Fetch the order from the database
        Optional<Order> optionalOrder = orderRepository.findById(orderId);
        if (optionalOrder.isEmpty()) {
            return false;  // If order not found, return failure
        }

        Order order = optionalOrder.get();

        // Simulate payment processing based on the payment method
        if (paymentRequest.getPaymentMethod().equalsIgnoreCase("credit_card")) {
            // Add payment gateway integration logic here if needed

            // If payment is successful, update the order status to "Paid"
            try {
                order.setOrderStatus("Paid");
                orderRepository.save(order);
                return true;
            } catch (Exception e) {
                e.printStackTrace();  // Log the error
                return false;
            }
        }

        // Return false if payment method is unsupported or payment fails
        return false;
    }
}
