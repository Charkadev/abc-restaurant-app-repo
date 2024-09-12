package com.abcrest.abcRestaurant.service;

import com.abcrest.abcRestaurant.model.Food;
import com.abcrest.abcRestaurant.repository.FoodRepository;
import com.abcrest.abcRestaurant.request.CreateFoodRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class FoodServiceImp implements FoodService {

    @Autowired
    private FoodRepository foodRepository;

    @Override
    public Food createFood(CreateFoodRequest req) {
        Food food = new Food();
        food.setDescription(req.getDescription());
        food.setImages(req.getImages());
        food.setName(req.getName());
        food.setPrice(req.getPrice());
        food.setCategory(req.getCategory());  // Category now stored as a string

        return foodRepository.save(food);
    }

    @Override
    public void deleteFood(String foodId) throws Exception {
        Food food = findFoodById(foodId);
        foodRepository.delete(food);
    }

    @Override
    public List<Food> searchFood(String keyword) {
        return foodRepository.searchFood(keyword);
    }

    @Override
    public Food findFoodById(String foodId) throws Exception {
        Optional<Food> optionalFood = foodRepository.findById(foodId);
        if (optionalFood.isEmpty()) {
            throw new Exception("Food does not exist...");
        }
        return optionalFood.get();
    }

    @Override
    public Food updateAvailabilityStatus(String foodId) throws Exception {
        Food food = findFoodById(foodId);
        food.setAvailable(!food.isAvailable());
        return foodRepository.save(food);
    }
}
