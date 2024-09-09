package com.abcrest.abcRestaurant.service;

import com.abcrest.abcRestaurant.model.Category;

import java.util.List;

public interface CategoryService {

    public Category createCategory(String name, String userId) throws Exception;  // String for MongoDB ObjectId

    public List<Category> findCategoryByRestaurantId(String restaurantId) throws Exception;  // String for MongoDB ObjectId

    public Category findCategoryById(String id) throws Exception;  // String for MongoDB ObjectId
}
