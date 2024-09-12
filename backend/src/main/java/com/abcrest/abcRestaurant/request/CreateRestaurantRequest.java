package com.abcrest.abcRestaurant.request;

import lombok.Data;
import java.util.List;

@Data
public class CreateRestaurantRequest {

    private String id;  // String for MongoDB ObjectId
    private String name;
    private String description;
    private String cuisineType;
    private String address;  // Use String instead of Address object
    private String contact;  // Use String instead of ContactInformation object
    private String openingHours;
    private List<String> images;  // List of image URLs
}
