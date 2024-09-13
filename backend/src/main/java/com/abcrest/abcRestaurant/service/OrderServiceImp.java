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
    private UserRepository userRepository;

    @Autowired
    private CartService cartService;

    @Override
    public Order createOrder(OrderRequest orderRequest, User user) {
        // Create a new Order object
        Order createdOrder = new Order();
        createdOrder.setUserId(user.getId());  // Setting userId instead of a User object
        createdOrder.setCreatedAt(new Date());
        createdOrder.setOrderStatus("Pending");
        createdOrder.setDeliveryAddress(orderRequest.getDeliveryAddress());  // Set the delivery address as a string

        // Fetch the user's cart
        Cart cart;
        try {
            cart = cartService.findCartByCustomerId(user.getId());
            if (cart == null || cart.getItems().isEmpty()) {
                throw new RuntimeException("Cart is empty or not found for user ID: " + user.getId());
            }
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

        // Calculate total items and price
        int totalItems = cart.getItems().stream().mapToInt(CartItem::getQuantity).sum();
        Long totalPrice;
        try {
            totalPrice = cartService.calculateCartTotals(cart);
        } catch (Exception e) {
            throw new RuntimeException("Failed to calculate cart totals for user ID: " + user.getId(), e);
        }

        // Set order items, total items, and total price
        createdOrder.setItems(orderItems);
        createdOrder.setTotalItem(totalItems);
        createdOrder.setTotalPrice(totalPrice);

        return orderRepository.save(createdOrder);
    }

    @Override
    public Order createTemporaryOrder(User user, OrderRequest orderRequest) throws Exception {
        // Fetch the user's cart
        Cart cart = cartService.findCartByCustomerId(user.getId());
        if (cart == null || cart.getItems().isEmpty()) {
            throw new RuntimeException("Cart is empty or not found for user ID: " + user.getId());
        }

        // Convert CartItems to OrderItems
        List<OrderItem> orderItems = new ArrayList<>();
        for (CartItem cartItem : cart.getItems()) {
            OrderItem orderItem = new OrderItem();
            orderItem.setFood(cartItem.getFood());
            orderItem.setQuantity(cartItem.getQuantity());
            orderItem.setTotalPrice(cartItem.getTotalPrice());

            // Save each OrderItem
            orderItems.add(orderItemRepository.save(orderItem));
        }

        // Create a new temporary order
        Order order = new Order();
        order.setUserId(user.getId());
        order.setOrderStatus("Pending Payment");
        order.setCreatedAt(new Date());
        order.setDeliveryAddress(orderRequest.getDeliveryAddress());
        order.setItems(orderItems);
        order.setTotalPrice(cart.getTotal());

        // Save and return the temporary order
        return orderRepository.save(order);
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
        // Get orders by userId
        return orderRepository.findByUserId(userId);
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
