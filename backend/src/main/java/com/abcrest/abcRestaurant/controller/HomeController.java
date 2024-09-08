package com.abcrest.abcRestaurant.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HomeController {

    public ResponseEntity<String> HomeController() {
        return new ResponseEntity<>("Welcome to restaurant project", HttpStatus.OK);
    }
}
