package com.abcrest.abcRestaurant.dto;

import lombok.Data;
import java.util.List;

@Data
public class RestaurantDto {

    private String id;  // Change this from Long to String for MongoDB compatibility

    private String title;
    private String description;
    private List<String> images;
}
