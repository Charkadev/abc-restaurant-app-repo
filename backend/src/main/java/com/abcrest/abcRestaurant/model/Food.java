package com.abcrest.abcRestaurant.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Date;
import java.util.List;
import java.util.ArrayList;

@Document(collection = "foods")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Food {

    @Id
    private String id;  // MongoDB ObjectId

    private String name;
    private String description;
    private Long price;
    private String category;  // String field for category
    private List<String> images = new ArrayList<>();
    private boolean available;
    private Date creationDate;
}
