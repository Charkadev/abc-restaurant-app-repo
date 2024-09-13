package com.abcrest.abcRestaurant.request;

import lombok.Data;

@Data
public class OrderRequest {

    private String deliveryAddress;  // Changed to String to match OrderServiceImp
}
