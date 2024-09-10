package com.abcrest.abcRestaurant.model;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;
import java.util.Date;

@Data
@Document(collection = "reservations")
public class Reservation {
    @Id
    private String id;

    @DBRef
    private User customer;  // Reference to the customer making the reservation

    private Date date;
    private String time;
    private int partySize;
    private String status;  // Status (e.g., PENDING, CONFIRMED, CANCELLED)
}
