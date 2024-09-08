package com.abcrest.abcRestaurant.model;

import lombok.Data;

@Data
public class ContactInformation { // Embedded in other documents, not a standalone collection

    private String email;
    private String mobile;
    private String twitter;
    private String instagram;

}
