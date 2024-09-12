package com.abcrest.abcRestaurant.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Date;
import java.util.List;

@Document(collection = "orders")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Order {

    @Id  // MongoDB automatically generates an ObjectId
    private String id;  // Changed to String to store MongoDB ObjectId

    @DBRef  // Reference to the User document
    private User customer;

    private Long totalAmount;
    private String orderStatus;
    private Date createdAt;

    @DBRef  // Reference to the Address document
    private Address deliveryAddress;

    @DBRef  // References to OrderItem documents
    private List<OrderItem> items;

    private int totalItem;
    private Long totalPrice;
}
