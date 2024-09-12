package com.abcrest.abcRestaurant.request;

import lombok.Data;

@Data
public class AddCartItemRequest {

    private String foodId;
    private int quantity;


}
