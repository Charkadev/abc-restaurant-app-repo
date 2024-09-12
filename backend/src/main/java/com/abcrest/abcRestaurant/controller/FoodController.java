package com.abcrest.abcRestaurant.controller;

import com.abcrest.abcRestaurant.model.Food;
import com.abcrest.abcRestaurant.repository.FoodRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/food")
public class FoodController {

    @Autowired
    private FoodRepository foodRepository;

    // Endpoint to fetch all food items (menu items)
    @GetMapping("/all")
    public ResponseEntity<List<Food>> getAllFoodItems() {
        List<Food> foods = foodRepository.findAll();
        return new ResponseEntity<>(foods, HttpStatus.OK);
    }

    // Create a new food item (menu item)
    @PostMapping("/create")
    public ResponseEntity<Food> createFoodItem(@RequestBody Food food) {
        Food savedFood = foodRepository.save(food);
        return new ResponseEntity<>(savedFood, HttpStatus.CREATED);
    }

    // Update an existing food item (menu item) by ID
    @PutMapping("/update/{id}")
    public ResponseEntity<Food> updateFoodItem(@PathVariable String id, @RequestBody Food updatedFood) {
        Optional<Food> foodOptional = foodRepository.findById(id);

        if (foodOptional.isPresent()) {
            Food food = foodOptional.get();
            food.setItem_name(updatedFood.getItem_name());  // Changed from 'setName' to 'setItem_name'
            food.setDescription(updatedFood.getDescription());
            food.setPrice(updatedFood.getPrice());
            food.setAvailable(updatedFood.isAvailable());

            Food savedFood = foodRepository.save(food);
            return new ResponseEntity<>(savedFood, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    // Delete a food item (menu item) by ID
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> deleteFoodItem(@PathVariable String id) {
        Optional<Food> foodOptional = foodRepository.findById(id);

        if (foodOptional.isPresent()) {
            foodRepository.delete(foodOptional.get());
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    // Search for food items by name or description
    @GetMapping("/search")
    public ResponseEntity<List<Food>> searchFoodItems(@RequestParam String query) {
        List<Food> foods = foodRepository.searchFood(query);
        return new ResponseEntity<>(foods, HttpStatus.OK);
    }
}
