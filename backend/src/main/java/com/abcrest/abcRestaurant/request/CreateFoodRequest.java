package com.abcrest.abcRestaurant.request;

import lombok.Data;
import java.util.List;

@Data
public class CreateFoodRequest {

    private String name;
    private String description;
    private Long price;
    private String category;  // String field for category
    private List<String> images;
}
