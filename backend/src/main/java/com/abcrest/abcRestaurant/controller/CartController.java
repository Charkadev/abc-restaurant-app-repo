package com.abcrest.abcRestaurant.controller;

import com.abcrest.abcRestaurant.model.Cart;
import com.abcrest.abcRestaurant.model.CartItem;
import com.abcrest.abcRestaurant.model.User;
import com.abcrest.abcRestaurant.request.AddCartItemRequest;
import com.abcrest.abcRestaurant.request.UpdateCartItemRequest;
import com.abcrest.abcRestaurant.service.CartService;
import com.abcrest.abcRestaurant.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
public class CartController {

    @Autowired
    private CartService cartService;

    @Autowired
    private UserService userService;

    @PutMapping("/cart/add")
    public ResponseEntity<CartItem> addItemToCart(@RequestBody AddCartItemRequest req,
                                                  @RequestHeader("Authorization") String jwt) throws Exception {
        CartItem cartItem = cartService.addItemToCart(req, jwt);
        return new ResponseEntity<>(cartItem, HttpStatus.OK);
    }

    @PutMapping("/cart-item/update")
    public ResponseEntity<CartItem> updateCartItemQuantity(
            @RequestBody UpdateCartItemRequest req,
            @RequestHeader("Authorization") String jwt) throws Exception {
        CartItem cartItem = cartService.updateCartItemQuantity(req.getCartItemId(), req.getQuantity());
        return new ResponseEntity<>(cartItem, HttpStatus.OK);
    }

//    @DeleteMapping("/cart-item/{id}/remove")
//    public ResponseEntity<?> removeCartItem(@PathVariable String id, @RequestHeader("Authorization") String jwt) {
//        try {
//            Cart cart = cartService.removeItemFromCart(id, jwt);
//            return new ResponseEntity<>(cart, HttpStatus.OK);
//        } catch (Exception e) {
//            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);  // Return the error message in the response
//        }
//    }

//    @DeleteMapping("/cart-item/{id}/remove")
//    public ResponseEntity<?> removeCartItem(@PathVariable String id, @RequestHeader("Authorization") String jwt) {
//        try {
//            Cart cart = cartService.removeItemFromCart(id, jwt);
//            return new ResponseEntity<>(cart, HttpStatus.OK);
//        } catch (Exception e) {
//            // Return the error message to the frontend
//            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
//        }
//    }

    @DeleteMapping("/cart-item/{id}/remove")
    public ResponseEntity<?> removeCartItem(@PathVariable String id, @RequestHeader("Authorization") String jwt) {
        try {
            Cart cart = cartService.removeItemFromCart(id, jwt);
            return new ResponseEntity<>(cart, HttpStatus.OK);
        } catch (Exception e) {
            // Return the error message to the frontend
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }




    @PutMapping("/cart/clear")
    public ResponseEntity<Cart> clearCart(@RequestHeader("Authorization") String jwt) throws Exception {
        User user = userService.findUserByJwtToken(jwt);
        Cart cart = cartService.clearCart(user.getId());
        return new ResponseEntity<>(cart, HttpStatus.OK);
    }

    @GetMapping("/cart")
    public ResponseEntity<Cart> findUserCart(@RequestHeader("Authorization") String jwt) throws Exception {
        User user = userService.findUserByJwtToken(jwt);
        Cart cart = cartService.findCartByCustomerId(user.getId());
        return new ResponseEntity<>(cart, HttpStatus.OK);
    }
}
