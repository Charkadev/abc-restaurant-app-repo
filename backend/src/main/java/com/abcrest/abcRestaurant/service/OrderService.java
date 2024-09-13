package com.abcrest.abcRestaurant.service;

import com.abcrest.abcRestaurant.model.Order;
import com.abcrest.abcRestaurant.model.User;
import com.abcrest.abcRestaurant.request.OrderRequest;

import java.util.List;

public interface OrderService {

    Order createOrder(OrderRequest order, User user);

    Order updateOrder(String orderId, String orderStatus) throws Exception;

    void cancelOrder(String orderId) throws Exception;

    List<Order> getUsersOrder(String userId) throws Exception;

    Order findOrderById(String orderId) throws Exception;
}
