package com.abcrest.abcRestaurant.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "categories")  // Specify the MongoDB collection name
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Category {

    @Id  // MongoDB generates an ObjectId automatically
    private String id;  // Changed to String to store the MongoDB ObjectId

    private String name;

    @JsonIgnore
    @DBRef  // Reference to the Restaurant document
    private Restaurant restaurant;
}
