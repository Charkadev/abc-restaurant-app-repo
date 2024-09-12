package com.abcrest.abcRestaurant.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Document(collection = "restaurants")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Restaurant {

    @Id
    private String id;

    private String name;
    private String description;
    private String address;
    private String contact;
    private String openingHours;
    private List<String> images = new ArrayList<>();
    private LocalDateTime registrationDate;
    private boolean open;


}
