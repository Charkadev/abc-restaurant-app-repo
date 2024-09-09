package com.abcrest.abcRestaurant.service;

import com.abcrest.abcRestaurant.model.IngredientCategory;
import com.abcrest.abcRestaurant.model.IngredientsItem;
import com.abcrest.abcRestaurant.model.Restaurant;
import com.abcrest.abcRestaurant.repository.IngredientCategoryRepository;
import com.abcrest.abcRestaurant.repository.IngredientItemRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class IngredientServiceImp implements IngredientsService {

    @Autowired
    private IngredientItemRepository ingredientItemRepository;

    @Autowired
    private IngredientCategoryRepository ingredientCategoryRepository;

    @Autowired
    private RestaurantService restaurantService;

    @Override
    public IngredientCategory createIngredientCategory(String name, String restaurantId) throws Exception {  // String for MongoDB ObjectId
        Restaurant restaurant = restaurantService.findRestaurantById(restaurantId);
        IngredientCategory category = new IngredientCategory();
        category.setRestaurant(restaurant);
        category.setName(name);
        return ingredientCategoryRepository.save(category);
    }

    @Override
    public IngredientCategory findIngredientCategoryById(String id) throws Exception {  // String for MongoDB ObjectId
        Optional<IngredientCategory> opt = ingredientCategoryRepository.findById(id);
        if (opt.isEmpty()) {
            throw new Exception("Ingredient Category not found");
        }
        return opt.get();
    }

    @Override
    public List<IngredientCategory> findIngredientCategoryByRestaurantId(String restaurantId) throws Exception {  // String for MongoDB ObjectId
        return ingredientCategoryRepository.findByRestaurantId(restaurantId);
    }

    @Override
    public IngredientsItem createIngredientItem(String restaurantId, String ingredientName, String categoryId) throws Exception {  // String for MongoDB ObjectId
        Restaurant restaurant = restaurantService.findRestaurantById(restaurantId);
        IngredientCategory category = findIngredientCategoryById(categoryId);

        IngredientsItem item = new IngredientsItem();
        item.setName(ingredientName);
        item.setRestaurant(restaurant);
        item.setCategory(category);

        IngredientsItem ingredient = ingredientItemRepository.save(item);
        category.getIngredients().add(ingredient);

        return ingredient;
    }

    @Override
    public List<IngredientsItem> findRestaurantsIngredients(String restaurantId) {
        return ingredientItemRepository.findByRestaurantId(restaurantId);
    }

    @Override
    public IngredientsItem updateStock(String id) throws Exception {  // String for MongoDB ObjectId
        Optional<IngredientsItem> optionalIngredientsItem = ingredientItemRepository.findById(id);
        if (optionalIngredientsItem.isEmpty()) {
            throw new Exception("Ingredient item not found");
        }
        IngredientsItem ingredientsItem = optionalIngredientsItem.get();
        ingredientsItem.setInStock(!ingredientsItem.isInStock());
        return ingredientItemRepository.save(ingredientsItem);
    }
}
