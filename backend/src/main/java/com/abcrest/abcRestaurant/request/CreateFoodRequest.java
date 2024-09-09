package com.abcrest.abcRestaurant.request;

import com.abcrest.abcRestaurant.model.Category;
import com.abcrest.abcRestaurant.model.IngredientsItem;
import lombok.Data;

import java.util.List;

@Data
public class CreateFoodRequest {

    private String name;
    private String description;
    private Long price;

    private Category category;
    private List<String> images;

    // Change restaurantId to String for MongoDB ObjectId
    private String restaurantId;

    private boolean vegetarian;
    private boolean seasonal;
    private List<IngredientsItem> ingredients;

}
