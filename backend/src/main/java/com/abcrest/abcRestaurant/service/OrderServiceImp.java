package com.abcrest.abcRestaurant.service;

import com.abcrest.abcRestaurant.model.*;
import com.abcrest.abcRestaurant.repository.*;
import com.abcrest.abcRestaurant.request.OrderRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class OrderServiceImp implements OrderService {

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private OrderItemRepository orderItemRepository;

    @Autowired
    private AddressRepository addressRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private CartService cartService;

    @Override
    public Order createOrder(OrderRequest orderRequest, User user) {
        // Save the delivery address
        Address deliveryAddress = orderRequest.getDeliveryAddress();
        Address savedAddress = addressRepository.save(deliveryAddress);

        // If the saved address is not already in the user's addresses, add it
        if (!user.getAddresses().contains(savedAddress)) {
            user.getAddresses().add(savedAddress);
            userRepository.save(user);
        }

        // Create a new Order object
        Order createdOrder = new Order();
        createdOrder.setCustomer(user);
        createdOrder.setCreatedAt(new Date());
        createdOrder.setOrderStatus("Pending");
        createdOrder.setDeliveryAddress(savedAddress);

        Cart cart;
        try {
            // Find the cart by user ID
            cart = cartService.findCartByUserId(user.getId());
        } catch (Exception e) {
            throw new RuntimeException("Failed to find cart for user ID: " + user.getId(), e);
        }

        // Convert cart items to order items
        List<OrderItem> orderItems = new ArrayList<>();
        for (CartItem cartItem : cart.getItems()) {
            OrderItem orderItem = new OrderItem();
            orderItem.setFood(cartItem.getFood());
            orderItem.setQuantity(cartItem.getQuantity());
            orderItem.setTotalPrice(cartItem.getTotalPrice());

            // Save the order item
            OrderItem savedOrderItem = orderItemRepository.save(orderItem);
            orderItems.add(savedOrderItem);
        }

        Long totalPrice;
        try {
            // Calculate the total price of the cart
            totalPrice = cartService.calculateCartTotals(cart);
        } catch (Exception e) {
            throw new RuntimeException("Failed to calculate cart totals for user ID: " + user.getId(), e);
        }

        createdOrder.setItems(orderItems);
        createdOrder.setTotalPrice(totalPrice);

        return orderRepository.save(createdOrder);
    }

    @Override
    public Order updateOrder(String orderId, String orderStatus) throws Exception {
        // Find the order by String orderId
        Order order = findOrderById(orderId);

        // Validate the order status and update it
        if (orderStatus.equalsIgnoreCase("OUT_FOR_DELIVERY")
                || orderStatus.equalsIgnoreCase("DELIVERED")
                || orderStatus.equalsIgnoreCase("COMPLETED")
                || orderStatus.equalsIgnoreCase("PENDING")) {
            order.setOrderStatus(orderStatus);
            return orderRepository.save(order);
        }
        throw new Exception("Please select a valid order status");
    }

    @Override
    public void cancelOrder(String orderId) throws Exception {
        // Find the order by String orderId
        Order order = findOrderById(orderId);
        orderRepository.deleteById(orderId);
    }

    @Override
    public List<Order> getUsersOrder(String userId) throws Exception {
        // Get orders by customerId
        return orderRepository.findByCustomerId(userId);
    }

    @Override
    public Order findOrderById(String orderId) throws Exception {
        // Find the order by String orderId
        Optional<Order> optionalOrder = orderRepository.findById(orderId);
        if (optionalOrder.isEmpty()) {
            throw new Exception("Order not found with id: " + orderId);
        }
        return optionalOrder.get();
    }
}
