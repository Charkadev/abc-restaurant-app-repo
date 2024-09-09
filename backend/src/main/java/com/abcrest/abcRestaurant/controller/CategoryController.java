package com.abcrest.abcRestaurant.controller;

import com.abcrest.abcRestaurant.model.Category;
import com.abcrest.abcRestaurant.model.User;
import com.abcrest.abcRestaurant.service.CategoryService;
import com.abcrest.abcRestaurant.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
public class CategoryController {

    @Autowired
    private CategoryService categoryService;

    @Autowired
    private UserService userService;

    // Admin and Restaurant Staff can create categories
    @PreAuthorize("hasAnyRole('ADMIN', 'RESTAURANT_STAFF')")
    @PostMapping("/admin/category")
    public ResponseEntity<Category> createCategory(@RequestBody Category category,
                                                   @RequestHeader("Authorization") String jwt) throws Exception {
        User user = userService.findUserByJwtToken(jwt);
        Category createdCategory = categoryService.createCategory(category.getName(), user.getId());
        return new ResponseEntity<>(createdCategory, HttpStatus.CREATED);
    }

    // Customers, Admin, and Restaurant Staff can get restaurant categories
    @PreAuthorize("hasAnyRole('ADMIN', 'RESTAURANT_STAFF', 'CUSTOMER')")
    @GetMapping("/category/restaurant")
    public ResponseEntity<List<Category>> getRestaurantCategory(@RequestHeader("Authorization") String jwt) throws Exception {
        User user = userService.findUserByJwtToken(jwt);
        List<Category> categories = categoryService.findCategoryByRestaurantId(user.getId());
        return new ResponseEntity<>(categories, HttpStatus.OK);
    }
}
