package com.abcrest.abcRestaurant.request;

import lombok.Data;

@Data
public class IngredientRequest {

    private String name;
    private String categoryId;  // String for MongoDB ObjectId
    private String restaurantId;  // String for MongoDB ObjectId
}
