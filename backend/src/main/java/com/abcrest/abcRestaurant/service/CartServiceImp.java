package com.abcrest.abcRestaurant.service;

import com.abcrest.abcRestaurant.model.Cart;
import com.abcrest.abcRestaurant.model.CartItem;
import com.abcrest.abcRestaurant.model.Food;
import com.abcrest.abcRestaurant.model.User;
import com.abcrest.abcRestaurant.repository.CartItemRepository;
import com.abcrest.abcRestaurant.repository.CartRepository;
import com.abcrest.abcRestaurant.repository.FoodRepository;
import com.abcrest.abcRestaurant.request.AddCartItemRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
public class CartServiceImp implements CartService {

    @Autowired
    private CartRepository cartRepository;

    @Autowired
    private UserService userService;

    @Autowired
    private CartItemRepository cartItemRepository;

    @Autowired
    private FoodRepository foodRepository;

    @Override
    public CartItem addItemToCart(AddCartItemRequest req, String jwt) throws Exception {
        User user = userService.findUserByJwtToken(jwt);

        Cart cart = cartRepository.findByUserId(user.getId())
                .orElseGet(() -> new Cart(user.getId(), 0L, new ArrayList<>()));

        Food food = foodRepository.findById(req.getFoodId()).orElseThrow(() -> new Exception("Food not found"));

        for (CartItem cartItem : cart.getItems()) {
            if (cartItem.getFood().equals(food)) {
                int newQuantity = cartItem.getQuantity() + req.getQuantity();
                return updateCartItemQuantity(cartItem.getId(), newQuantity);
            }
        }

        CartItem newCartItem = new CartItem();
        newCartItem.setFood(food);
        newCartItem.setCart(cart);
        newCartItem.setQuantity(req.getQuantity());
        newCartItem.setTotalPrice(req.getQuantity() * food.getPrice());

        CartItem savedCartItem = cartItemRepository.save(newCartItem);
        cart.getItems().add(savedCartItem);
        cartRepository.save(cart);

        return savedCartItem;
    }

    @Override
    public CartItem updateCartItemQuantity(String cartItemId, int quantity) throws Exception {
        CartItem item = cartItemRepository.findById(cartItemId).orElseThrow(() -> new Exception("CartItem not found"));
        item.setQuantity(quantity);
        item.setTotalPrice(item.getFood().getPrice() * quantity);
        return cartItemRepository.save(item);
    }

//    @Override
//    public Cart removeItemFromCart(String cartItemId, String jwt) throws Exception {
//        User user = userService.findUserByJwtToken(jwt);
//        Cart cart = cartRepository.findByUserId(user.getId())
//                .orElseThrow(() -> new Exception("Cart not found for user ID: " + user.getId()));
//
//        CartItem item = cartItemRepository.findById(cartItemId)
//                .orElseThrow(() -> new Exception("CartItem not found with ID: " + cartItemId));
//
//        boolean removed = cart.getItems().remove(item);
//
//        if (!removed) {
//            throw new Exception("Failed to remove the item from the cart.");
//        }
//
//        cartRepository.save(cart);
//        return cart;
//    }

    @Override
    public Cart removeItemFromCart(String cartItemId, String jwt) throws Exception {
        // Find the user based on the JWT
        User user = userService.findUserByJwtToken(jwt);

        // Find the cart based on the user's ID
        Cart cart = cartRepository.findByUserId(user.getId())
                .orElseThrow(() -> new Exception("Cart not found for user ID: " + user.getId()));

        // Find the cart item in the repository
        CartItem item = cartItemRepository.findById(cartItemId)
                .orElseThrow(() -> new Exception("CartItem not found with ID: " + cartItemId));

        // Check if the cart contains the item and remove it
        boolean removed = cart.getItems().removeIf(cartItem -> cartItem.getId().equals(cartItemId));

        if (!removed) {
            throw new Exception("Failed to remove the item from the cart.");
        }

        // Save the updated cart without the removed item
        cartRepository.save(cart);

        // Now remove the item from the CartItem collection as well
        cartItemRepository.deleteById(cartItemId);  // Deleting CartItem from MongoDB

        return cart;
    }




    @Override
    public Long calculateCartTotals(Cart cart) {
        return cart.getItems().stream()
                .mapToLong(cartItem -> cartItem.getFood().getPrice() * cartItem.getQuantity())
                .sum();
    }

    @Override
    public Cart findCartById(String id) throws Exception {
        return cartRepository.findById(id).orElseThrow(() -> new Exception("Cart not found with id: " + id));
    }

    @Override
    public Cart findCartByCustomerId(String userId) throws Exception {
        Cart cart = cartRepository.findByUserId(userId)
                .orElseThrow(() -> new Exception("Cart not found for user ID: " + userId));
        cart.setTotal(calculateCartTotals(cart));
        return cart;
    }

    @Override
    public Cart clearCart(String userId) throws Exception {
        Cart cart = findCartByCustomerId(userId);
        cart.getItems().clear();
        return cartRepository.save(cart);
    }
}
