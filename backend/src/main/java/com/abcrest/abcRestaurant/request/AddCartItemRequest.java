package com.abcrest.abcRestaurant.request;

import lombok.Data;
import java.util.List;

@Data
public class AddCartItemRequest {

    private String foodId;
    private int quantity;
    private List<String> ingredients;


}
