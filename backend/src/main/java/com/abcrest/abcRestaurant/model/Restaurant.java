package com.abcrest.abcRestaurant.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Document(collection = "restaurants")  // MongoDB collection name
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Restaurant {

    @Id  // MongoDB generates a unique ObjectId automatically
    private String id;

    @DBRef  // Reference to the User document (owner)
    private User owner;

    private String name;
    private String description;
    private String cuisineType;
    private String address;  // Use String to store the address
    private String contact;  // Use String to store contact information

    private String openingHours;

    @DBRef  // Reference to the Order documents
    private List<Order> orders = new ArrayList<>();

    private List<String> images = new ArrayList<>();  // List of image URLs

    private LocalDateTime registrationDate;
    private boolean open;

    @JsonIgnore
    @DBRef  // Reference to the Food documents
    private List<Food> foods = new ArrayList<>();
}
