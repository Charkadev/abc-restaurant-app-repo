package com.abcrest.abcRestaurant.request;

import lombok.Data;

@Data
public class AddCartItemRequest {

    private String foodId;  // ID of the food item to be added to the cart
    private int quantity;   // Quantity of the food item to be added
}
