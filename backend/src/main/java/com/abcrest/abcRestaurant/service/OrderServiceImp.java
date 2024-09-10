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
import java.util.stream.Collectors;

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
    private RestaurantService restaurantService;

    @Autowired
    private CartService cartService;

    // Removed `throws Exception` to match the interface method signature
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

        // Find the restaurant by String id (MongoDB ObjectId)
        String restaurantId = String.valueOf(orderRequest.getRestaurantId());
        Restaurant restaurant;
        try {
            restaurant = restaurantService.findRestaurantById(restaurantId);
        } catch (Exception e) {
            throw new RuntimeException("Failed to find restaurant with ID: " + restaurantId, e);
        }

        // Create a new Order object
        Order createdOrder = new Order();
        createdOrder.setCustomer(user);
        createdOrder.setCreatedAt(new Date());
        createdOrder.setOrderStatus("Pending");
        createdOrder.setDeliveryAddress(savedAddress);
        createdOrder.setRestaurant(restaurant);

        // Find the cart by user ID (Exception handling)
        Cart cart;
        try {
            cart = cartService.findCartByUserId(user.getId());
        } catch (Exception e) {
            throw new RuntimeException("Failed to retrieve cart for user ID: " + user.getId(), e);
        }

        // Convert cart items to order items
        List<OrderItem> orderItems = new ArrayList<>();
        for (CartItem cartItem : cart.getItems()) {
            OrderItem orderItem = new OrderItem();
            orderItem.setFood(cartItem.getFood());
            orderItem.setIngredients(cartItem.getIngredients());
            orderItem.setQuantity(cartItem.getQuantity());
            orderItem.setTotalPrice(cartItem.getTotalPrice());

            // Save the order item and add it to the order items list
            OrderItem savedOrderItem = orderItemRepository.save(orderItem);
            orderItems.add(savedOrderItem);
        }

        // Calculate the total price of the cart (Exception handling)
        Long totalPrice;
        try {
            totalPrice = cartService.calculateCartTotals(cart);
        } catch (Exception e) {
            throw new RuntimeException("Failed to calculate cart totals for user ID: " + user.getId(), e);
        }
        createdOrder.setItems(orderItems);
        createdOrder.setTotalPrice(totalPrice);

        // Save the order and associate it with the restaurant
        Order savedOrder = orderRepository.save(createdOrder);
        restaurant.getOrders().add(savedOrder);

        return createdOrder;
    }

    @Override
    public Order updateOrder(String orderId, String orderStatus) throws Exception {
        // Find the order by String orderId (MongoDB ObjectId)
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
        // Find the order by String orderId (MongoDB ObjectId)
        Order order = findOrderById(orderId);
        orderRepository.deleteById(orderId);
    }

    @Override
    public List<Order> getUsersOrder(String userId) throws Exception {
        // Get orders by customerId (String for MongoDB ObjectId)
        return orderRepository.findByCustomerId(userId);
    }

    @Override
    public List<Order> getRestaurantsOrder(String restaurantId, String orderStatus) throws Exception {
        // Get orders by restaurantId (String for MongoDB ObjectId)
        List<Order> orders = orderRepository.findByRestaurantId(restaurantId);

        // Filter orders by order status if provided
        if (orderStatus != null) {
            orders = orders.stream()
                    .filter(order -> order.getOrderStatus().equalsIgnoreCase(orderStatus))
                    .collect(Collectors.toList());
        }
        return orders;
    }

    @Override
    public Order findOrderById(String orderId) throws Exception {
        // Find the order by String orderId (MongoDB ObjectId)
        Optional<Order> optionalOrder = orderRepository.findById(orderId);
        if (optionalOrder.isEmpty()) {
            throw new Exception("Order not found with id: " + orderId);
        }
        return optionalOrder.get();
    }
}
