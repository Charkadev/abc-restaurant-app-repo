package com.abcrest.abcRestaurant.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

@Document(collection = "cartItems")  // MongoDB collection name
@Data
@NoArgsConstructor
@AllArgsConstructor
public class CartItem {

    @Id  // MongoDB generates a unique ObjectId for this field
    private String id;  // Use String to store MongoDB ObjectId

    @JsonIgnore
    @DBRef  // Reference to the Cart document
    private Cart cart;

    @DBRef  // Reference to the Food document
    private Food food;

    private int quantity;

    private List<String> ingredients;

    private Long totalPrice;
}
