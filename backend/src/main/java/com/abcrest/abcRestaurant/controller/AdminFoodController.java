package com.abcrest.abcRestaurant.controller;

import com.abcrest.abcRestaurant.model.Food;
import com.abcrest.abcRestaurant.model.Restaurant;
import com.abcrest.abcRestaurant.model.User;
import com.abcrest.abcRestaurant.request.CreateFoodRequest;
import com.abcrest.abcRestaurant.response.MessageResponse;
import com.abcrest.abcRestaurant.service.FoodService;
import com.abcrest.abcRestaurant.service.RestaurantService;
import com.abcrest.abcRestaurant.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/admin/menu/items")
public class AdminFoodController {

    @Autowired
    private FoodService foodService;

    @Autowired
    private UserService userService;

    @Autowired
    private RestaurantService restaurantService;

    // Only Admin and Restaurant Staff can create food
    @PreAuthorize("hasAnyRole('ADMIN', 'RESTAURANT_STAFF')")
    @PostMapping
    public ResponseEntity<Food> createFood(@RequestBody CreateFoodRequest req,
                                           @RequestHeader("Authorization") String jwt) throws Exception {
        User user = userService.findUserByJwtToken(jwt);
        Restaurant restaurant = restaurantService.findRestaurantById(req.getRestaurantId());
        Food food = foodService.createFood(req, req.getCategory(), restaurant);

        return new ResponseEntity<>(food, HttpStatus.CREATED);
    }

    // Only Admin and Restaurant Staff can delete food
    @PreAuthorize("hasAnyRole('ADMIN', 'RESTAURANT_STAFF')")
    @DeleteMapping("/{id}")
    public ResponseEntity<MessageResponse> deleteFood(@PathVariable String id,
                                                      @RequestHeader("Authorization") String jwt) throws Exception {
        User user = userService.findUserByJwtToken(jwt);
        foodService.deleteFood(id);  // String for MongoDB ObjectId

        MessageResponse res = new MessageResponse();
        res.setMessage("Food deleted successfully");
        return new ResponseEntity<>(res, HttpStatus.OK);
    }

    // Only Admin and Restaurant Staff can update food availability
    @PreAuthorize("hasAnyRole('ADMIN', 'RESTAURANT_STAFF')")
    @PutMapping("/{id}/availability")
    public ResponseEntity<Food> updateFoodAvailabilityStatus(@PathVariable String id,
                                                             @RequestHeader("Authorization") String jwt) throws Exception {
        User user = userService.findUserByJwtToken(jwt);
        Food food = foodService.updateAvailabilityStatus(id);

        return new ResponseEntity<>(food, HttpStatus.OK);
    }
}
