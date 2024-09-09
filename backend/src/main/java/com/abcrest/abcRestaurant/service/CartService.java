package com.abcrest.abcRestaurant.service;

import com.abcrest.abcRestaurant.model.Cart;
import com.abcrest.abcRestaurant.model.CartItem;
import com.abcrest.abcRestaurant.request.AddCartItemRequest;

public interface CartService {
    CartItem addItemToCart(AddCartItemRequest req, String jwt) throws Exception;
    CartItem updateCartItemQuantitiy(String cartItemId, int quantity) throws Exception;  // Change Long to String
    Cart removeItemFromCart(String cartItemId, String jwt) throws Exception;  // Change Long to String
    Long calculateCartTotals(Cart cart) throws Exception;
    Cart findCartById(String id) throws Exception;  // Change Long to String
    Cart findCartByUserId(String jwt) throws Exception;
    Cart clearCart(String jwt) throws Exception;
}
