package com.abcrest.abcRestaurant.service;

import com.abcrest.abcRestaurant.model.Order;
import com.abcrest.abcRestaurant.model.User;
import com.abcrest.abcRestaurant.request.OrderRequest;

import java.util.List;

public interface OrderService {

    public Order createOrder(OrderRequest order, User user);

    public Order updateOrder(String orderId, String orderStatus) throws Exception;  // Changed Long to String

    public void cancelOrder(String orderId) throws Exception;  // Changed Long to String

    public List<Order> getUsersOrder(String userId) throws Exception;  // Changed Long to String

    public List<Order> getRestaurantsOrder(String restaurantId, String orderStatus) throws Exception;  // Changed Long to String

    public Order findOrderById(String orderId) throws Exception;  // Changed Long to String
}
