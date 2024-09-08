package com.abcrest.abcRestaurant.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

@Document(collection = "orderItems")  // MongoDB collection name
@Data
@NoArgsConstructor
@AllArgsConstructor
public class OrderItem {

    @Id  // MongoDB automatically generates a unique ObjectId
    private String id;  // Use String for MongoDB ObjectId

    @DBRef  // Reference to the Food document
    private Food food;

    private int quantity;

    private Long totalPrice;

    private List<String> ingredients;  // Embedded list of ingredients (or strings)
}
