package com.abcrest.abcRestaurant.service;

import com.abcrest.abcRestaurant.model.IngredientCategory;
import com.abcrest.abcRestaurant.model.IngredientsItem;

import java.util.List;

public interface IngredientsService {

    public IngredientCategory createIngredientCategory(String name, String restaurantId) throws Exception;  // String for MongoDB ObjectId

    public IngredientCategory findIngredientCategoryById(String id) throws Exception;  // String for MongoDB ObjectId

    public List<IngredientCategory> findIngredientCategoryByRestaurantId(String restaurantId) throws Exception;  // String for MongoDB ObjectId

    public IngredientsItem createIngredientItem(String restaurantId, String ingredientName, String categoryId) throws Exception;  // String for MongoDB ObjectId

    public List<IngredientsItem> findRestaurantsIngredients(String restaurantId);  // String for MongoDB ObjectId

    public IngredientsItem updateStock(String id) throws Exception;  // String for MongoDB ObjectId
}
