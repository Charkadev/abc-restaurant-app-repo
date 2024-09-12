package com.abcrest.abcRestaurant.request;

import com.abcrest.abcRestaurant.model.Address;
import lombok.Data;

@Data
public class OrderRequest {

    private Address deliveryAddress;


}
