package com.abcrest.abcRestaurant.service;

import com.abcrest.abcRestaurant.model.Cart;
import com.abcrest.abcRestaurant.model.CartItem;
import com.abcrest.abcRestaurant.request.AddCartItemRequest;

public interface CartService {
    CartItem addItemToCart(AddCartItemRequest req, String jwt) throws Exception;
    CartItem updateCartItemQuantity(String cartItemId, int quantity) throws Exception;
    Cart removeItemFromCart(String cartItemId, String jwt) throws Exception;
    Long calculateCartTotals(Cart cart) throws Exception;
    Cart findCartById(String id) throws Exception;
    Cart findCartByCustomerId(String userId) throws Exception;
    Cart clearCart(String userId) throws Exception;
}
