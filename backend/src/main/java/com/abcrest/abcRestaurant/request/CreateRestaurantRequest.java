package com.abcrest.abcRestaurant.request;

import com.abcrest.abcRestaurant.model.Address;
import com.abcrest.abcRestaurant.model.ContactInformation;
import lombok.Data;

import java.util.List;

@Data
public class CreateRestaurantRequest {

    private String id;  // String for MongoDB ObjectId
    private String name;
    private String description;
    private String cuisineType;
    private Address address;  // Embedded Address document
    private ContactInformation contactInformation;  // Embedded ContactInformation document
    private String openingHours;
    private List<String> images;  // List of image URLs
}
