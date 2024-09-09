package com.abcrest.abcRestaurant.request;

import lombok.Data;

@Data
public class UpdateCartItemRequest {
    private String cartItemId;
    private int quantity;
}
