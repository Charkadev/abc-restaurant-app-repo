package com.abcrest.abcRestaurant.service;

import com.abcrest.abcRestaurant.model.Category;
import com.abcrest.abcRestaurant.model.Restaurant;
import com.abcrest.abcRestaurant.repository.CategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CategoryServiceImp implements CategoryService {

    @Autowired
    private RestaurantService restaurantService;

    @Autowired
    private CategoryRepository categoryRepository;

    // Propagating the exception in the createCategory method
    @Override
    public Category createCategory(String name, String userId) throws Exception {
        // Fetching Restaurant by String userId for MongoDB ObjectId
        Restaurant restaurant = restaurantService.findRestaurantByUserId(userId);
        Category category = new Category();
        category.setName(name);
        category.setRestaurant(restaurant);
        return categoryRepository.save(category);
    }

    @Override
    public List<Category> findCategoryByRestaurantId(String restaurantId) {
        // Ensuring the repository query works with String for MongoDB ObjectId
        return categoryRepository.findByRestaurantId(restaurantId);
    }

    @Override
    public Category findCategoryById(String id) throws Exception {
        // Handling of String ObjectId for MongoDB
        Optional<Category> optionalCategory = categoryRepository.findById(id);
        if (optionalCategory.isEmpty()) {
            throw new Exception("Category not found");
        }
        return optionalCategory.get();
    }
}
