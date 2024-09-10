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
        Food food = foodRepository.findById(req.getFoodId()).orElseThrow(() -> new Exception("Food not found"));
        Cart cart = cartRepository.findByCustomerId(user.getId());

        for (CartItem cartItem : cart.getItems()) {
            if (cartItem.getFood().equals(food)) {
                int newQuantity = cartItem.getQuantity() + req.getQuantity();
                return updateCartItemQuantitiy(cartItem.getId(), newQuantity);
            }
        }

        CartItem newCartItem = new CartItem();
        newCartItem.setFood(food);
        newCartItem.setCart(cart);
        newCartItem.setQuantity(req.getQuantity());
        newCartItem.setIngredients(req.getIngredients());
        newCartItem.setTotalPrice(req.getQuantity() * food.getPrice());

        CartItem savedCartItem = cartItemRepository.save(newCartItem);
        cart.getItems().add(savedCartItem);
        return savedCartItem;
    }

    @Override
    public CartItem updateCartItemQuantitiy(String cartItemId, int quantity) throws Exception {
        CartItem item = cartItemRepository.findById(cartItemId).orElseThrow(() -> new Exception("CartItem not found"));
        item.setQuantity(quantity);
        item.setTotalPrice(item.getFood().getPrice() * quantity);
        return cartItemRepository.save(item);
    }

    @Override
    public Cart removeItemFromCart(String cartItemId, String jwt) throws Exception {
        User user = userService.findUserByJwtToken(jwt);
        Cart cart = cartRepository.findByCustomerId(user.getId());
        CartItem item = cartItemRepository.findById(cartItemId).orElseThrow(() -> new Exception("CartItem not found"));
        cart.getItems().remove(item);
        return cartRepository.save(cart);
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
    public Cart findCartByUserId(String userId) throws Exception {
        Cart cart = cartRepository.findByCustomerId(userId);
        cart.setTotal(calculateCartTotals(cart));
        return cart;
    }

    @Override
    public Cart clearCart(String userId) throws Exception {
        Cart cart = findCartByUserId(userId);
        cart.getItems().clear();
        return cartRepository.save(cart);
    }
}
