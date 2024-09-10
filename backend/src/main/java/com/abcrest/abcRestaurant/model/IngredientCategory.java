package com.abcrest.abcRestaurant.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.ArrayList;
import java.util.List;

@Document(collection = "ingredientCategories")  // MongoDB collection name
@Data
@NoArgsConstructor
@AllArgsConstructor
public class IngredientCategory {

    @Id  // MongoDB generates a unique ObjectId automatically
    private String id;  // Changed to String to store MongoDB ObjectId

    private String name;

    @JsonIgnore
    @DBRef  // Reference to the Restaurant document
    private Restaurant restaurant;

    @JsonIgnore
    @DBRef  // References to IngredientsItem documents
    private List<IngredientsItem> ingredients = new ArrayList<>();
}
