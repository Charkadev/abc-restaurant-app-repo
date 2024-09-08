package com.abcrest.abcRestaurant.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "ingredients")  // MongoDB collection name
@Data
@NoArgsConstructor
@AllArgsConstructor
public class IngredientsItem {

    @Id  // MongoDB will automatically generate an ObjectId
    private String id;  // Changed to String to store MongoDB ObjectId

    private String name;

    @DBRef  // Reference to the IngredientCategory document
    private IngredientCategory category;

    @JsonIgnore
    @DBRef  // Reference to the Restaurant document
    private Restaurant restaurant;

    private boolean inStock = true;
}
