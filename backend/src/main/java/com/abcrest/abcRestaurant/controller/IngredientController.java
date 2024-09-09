package com.abcrest.abcRestaurant.controller;

import com.abcrest.abcRestaurant.model.IngredientCategory;
import com.abcrest.abcRestaurant.model.IngredientsItem;
import com.abcrest.abcRestaurant.request.IngredientCategoryRequest;
import com.abcrest.abcRestaurant.request.IngredientRequest;
import com.abcrest.abcRestaurant.service.IngredientsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/admin/ingredients")
public class IngredientController {

    @Autowired
    private IngredientsService ingredientsService;

    // Admin and Restaurant Staff can create ingredient categories
    @PreAuthorize("hasAnyRole('ADMIN', 'RESTAURANT_STAFF')")
    @PostMapping("/category")
    public ResponseEntity<IngredientCategory> createIngredientCategory(
            @RequestBody IngredientCategoryRequest req
    ) throws Exception {
        IngredientCategory item = ingredientsService.createIngredientCategory(req.getName(), req.getRestaurantId());
        return new ResponseEntity<>(item, HttpStatus.CREATED);
    }

    // Admin and Restaurant Staff can create ingredient items
    @PreAuthorize("hasAnyRole('ADMIN', 'RESTAURANT_STAFF')")
    @PostMapping
    public ResponseEntity<IngredientsItem> createIngredientItem(
            @RequestBody IngredientRequest req
    ) throws Exception {
        IngredientsItem item = ingredientsService.createIngredientItem(req.getRestaurantId(), req.getName(), req.getCategoryId());
        return new ResponseEntity<>(item, HttpStatus.CREATED);
    }

    // Admin and Restaurant Staff can update ingredient stock
    @PreAuthorize("hasAnyRole('ADMIN', 'RESTAURANT_STAFF')")
    @PutMapping("/{id}/stock")
    public ResponseEntity<IngredientsItem> updateIngredientStock(
            @PathVariable String id  // Changed to String for MongoDB
    ) throws Exception {
        IngredientsItem item = ingredientsService.updateStock(id);
        return new ResponseEntity<>(item, HttpStatus.OK);
    }

    // Customers, Admin, and Restaurant Staff can view restaurant ingredients
    @PreAuthorize("hasAnyRole('ADMIN', 'RESTAURANT_STAFF', 'CUSTOMER')")
    @GetMapping("/restaurant/{id}")
    public ResponseEntity<List<IngredientsItem>> getRestaurantIngredient(
            @PathVariable String id  // Changed to String for MongoDB
    ) throws Exception {
        List<IngredientsItem> items = ingredientsService.findRestaurantsIngredients(id);
        return new ResponseEntity<>(items, HttpStatus.OK);
    }

    // Customers, Admin, and Restaurant Staff can view ingredient categories
    @PreAuthorize("hasAnyRole('ADMIN', 'RESTAURANT_STAFF', 'CUSTOMER')")
    @GetMapping("/restaurant/{id}/category")
    public ResponseEntity<List<IngredientCategory>> getRestaurantIngredientCategory(
            @PathVariable String id  // Changed to String for MongoDB
    ) throws Exception {
        List<IngredientCategory> items = ingredientsService.findIngredientCategoryByRestaurantId(id);
        return new ResponseEntity<>(items, HttpStatus.OK);
    }
}
