package com.abcrest.abcRestaurant.service;

import com.abcrest.abcRestaurant.model.Cart;
import com.abcrest.abcRestaurant.model.CartItem;
import com.abcrest.abcRestaurant.request.AddCartItemRequest;

public interface CartService {
    CartItem addItemToCart(AddCartItemRequest req, String jwt) throws Exception;
    CartItem updateCartItemQuantitiy(String cartItemId, int quantity) throws Exception;  // Changed Long to String
    Cart removeItemFromCart(String cartItemId, String jwt) throws Exception;  // Changed Long to String
    Long calculateCartTotals(Cart cart) throws Exception;
    Cart findCartById(String id) throws Exception;  // Changed Long to String
    Cart findCartByUserId(String userId) throws Exception;  // Changed Long to String
    Cart clearCart(String userId) throws Exception;  // Changed Long to String
}
