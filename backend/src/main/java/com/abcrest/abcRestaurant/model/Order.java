package com.abcrest.abcRestaurant.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Date;
import java.util.List;

@Document(collection = "orders")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Order {

    @Id  // MongoDB automatically generates an ObjectId
    private String id;  // String to store MongoDB ObjectId

    private String userId;  // Reference to the User's ID

    private Long totalAmount;
    private String orderStatus;
    private Date createdAt;

    private String deliveryAddress;  // Can be a simple string for now

    private List<OrderItem> items;  // Changed from CartItem to OrderItem

    private int totalItem;
    private Long totalPrice;
}
