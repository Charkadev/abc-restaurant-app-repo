package com.abcrest.abcRestaurant.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Date;
import java.util.List;
import java.util.ArrayList;

@Document(collection = "foods")  // Specify the MongoDB collection name
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Food {

    @Id  // MongoDB generates an ObjectId automatically
    private String id;  // Use String to store MongoDB ObjectId

    private String name;
    private String description;
    private Long price;

    @DBRef  // Reference to the Category document
    private Category foodCategory;

    private List<String> images = new ArrayList<>();  // Embedded list of image URLs

    private boolean available;

    @DBRef  // Reference to the Restaurant document
    private Restaurant restaurant;

    private boolean isVegetarian;
    private boolean isSeasonal;

    @DBRef  // References to IngredientsItem documents
    private List<IngredientsItem> ingredients = new ArrayList<>();

    private Date creationDate;
}
