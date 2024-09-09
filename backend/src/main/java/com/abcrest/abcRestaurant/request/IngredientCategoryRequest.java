package com.abcrest.abcRestaurant.request;

import lombok.Data;

@Data
public class IngredientCategoryRequest {
    private String name;
    private String restaurantId;  // String for MongoDB ObjectId
}
