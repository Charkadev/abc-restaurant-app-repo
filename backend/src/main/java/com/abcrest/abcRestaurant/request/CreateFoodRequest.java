package com.abcrest.abcRestaurant.request;

import lombok.Data;
import java.util.List;

@Data
public class CreateFoodRequest {

    private String item_name;  // Changed from 'name' to 'item_name'
    private String description;
    private Long price;
    private String category;  // String field for category (e.g., "Food", "Drinks", "Dessert")
    private List<String> images;  // Optional
}
