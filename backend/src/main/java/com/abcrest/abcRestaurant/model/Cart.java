package com.abcrest.abcRestaurant.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.ArrayList;
import java.util.List;

@Document(collection = "carts")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Cart {

    @Id
    private String id;

    private String userId;  // Reference to the User's ID

    private Long total;

    private List<CartItem> items = new ArrayList<>();

    // Corrected constructor
    public Cart(String userId, Long total, List<CartItem> items) {
        this.userId = userId;
        this.total = total;
        this.items = items;
    }
}
