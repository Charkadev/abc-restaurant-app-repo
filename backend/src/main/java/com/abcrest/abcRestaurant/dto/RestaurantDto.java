package com.abcrest.abcRestaurant.dto;

import lombok.Data;
import java.util.List;

@Data
public class RestaurantDto {

    private String title;

    private List<String> images;  // MongoDB supports storing lists directly

    private String description;
    private Long id;  // Assuming this is an identifier for reference, but typically MongoDB uses String for _id
}
