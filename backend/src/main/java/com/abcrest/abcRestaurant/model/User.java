package com.abcrest.abcRestaurant.model;

import com.abcrest.abcRestaurant.dto.RestaurantDto;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.ArrayList;
import java.util.List;

@Document(collection = "users")  // MongoDB collection name
@Data
@AllArgsConstructor
@NoArgsConstructor
public class User {

    @Id  // MongoDB generates a unique ObjectId automatically
    private String id;  // Use String for MongoDB ObjectId

    private String fullName;
    private String email;

    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)  // Password is write-only
    private String password;

    private USER_ROLE role = USER_ROLE.ROLE_CUSTOMER;  // Default role is ROLE_CUSTOMER

    @JsonIgnore  // Orders are not serialized when returning user data
    @DBRef  // Reference to the Order documents
    private List<Order> orders = new ArrayList<>();

    private List<RestaurantDto> favorites = new ArrayList<>();  // Embedded list of favorite restaurants

    @DBRef  // Reference to Address documents
    private List<Address> addresses = new ArrayList<>();
}
