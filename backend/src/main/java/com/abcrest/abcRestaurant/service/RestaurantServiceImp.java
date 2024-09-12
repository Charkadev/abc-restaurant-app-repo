package com.abcrest.abcRestaurant.service;

import com.abcrest.abcRestaurant.dto.RestaurantDto;
import com.abcrest.abcRestaurant.model.Restaurant;
import com.abcrest.abcRestaurant.model.User;
import com.abcrest.abcRestaurant.repository.RestaurantRepository;
import com.abcrest.abcRestaurant.repository.UserRepository;
import com.abcrest.abcRestaurant.request.CreateRestaurantRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class RestaurantServiceImp implements RestaurantService {

    @Autowired
    private RestaurantRepository restaurantRepository;

    @Autowired
    private UserRepository userRepository;

    @Override
    public Restaurant createRestaurant(CreateRestaurantRequest req, User user) {
        Restaurant restaurant = new Restaurant();
        restaurant.setAddress(req.getAddress());
        restaurant.setContact(req.getContact());
        restaurant.setDescription(req.getDescription());
        restaurant.setImages(req.getImages());
        restaurant.setName(req.getName());
        restaurant.setOpeningHours(req.getOpeningHours());
        restaurant.setRegistrationDate(LocalDateTime.now());
        restaurant.setOwner(user);
        return restaurantRepository.save(restaurant);
    }

    @Override
    public Restaurant updateRestaurant(String restaurantId, CreateRestaurantRequest updatedRestaurant) throws Exception {
        Restaurant restaurant = findRestaurantById(restaurantId);
        if (updatedRestaurant.getDescription() != null) {
            restaurant.setDescription(updatedRestaurant.getDescription());
        }
        if (updatedRestaurant.getName() != null) {
            restaurant.setName(updatedRestaurant.getName());
        }
        if (updatedRestaurant.getAddress() != null) {
            restaurant.setAddress(updatedRestaurant.getAddress());
        }
        if (updatedRestaurant.getContact() != null) {
            restaurant.setContact(updatedRestaurant.getContact());
        }
        return restaurantRepository.save(restaurant);
    }

    @Override
    public void deleteRestaurant(String restaurantId) throws Exception {
        Restaurant restaurant = findRestaurantById(restaurantId);
        restaurantRepository.delete(restaurant);
    }

    @Override
    public List<Restaurant> getAllRestaurant() {
        return restaurantRepository.findAll();
    }

    @Override
    public List<Restaurant> searchRestaurant(String keyword) {
        return restaurantRepository.findBySearchQuery(keyword);
    }

    @Override
    public Restaurant findRestaurantById(String id) throws Exception {
        Optional<Restaurant> opt = restaurantRepository.findById(id);
        if (opt.isEmpty()) {
            throw new Exception("Restaurant not found with id: " + id);
        }
        return opt.get();
    }

    @Override
    public Restaurant findRestaurantByUserId(String userId) throws Exception {
        Restaurant restaurant = restaurantRepository.findByOwnerId(userId);
        if (restaurant == null) {
            throw new Exception("Restaurant not found for user ID: " + userId);
        }
        return restaurant;
    }

    @Override
    public RestaurantDto addToFavorites(String restaurantId, User user) throws Exception {
        // If user is passed as null (e.g., for public endpoints), handle appropriately
        if (user == null) {
            throw new Exception("User must be provided for this operation.");
        }
        Restaurant restaurant = findRestaurantById(restaurantId);

        RestaurantDto dto = new RestaurantDto();
        dto.setDescription(restaurant.getDescription());
        dto.setImages(restaurant.getImages());
        dto.setTitle(restaurant.getName());
        dto.setId(restaurantId);

        boolean isFavorited = false;
        List<RestaurantDto> favorites = user.getFavorites();
        for (RestaurantDto favorite : favorites) {
            if (favorite.getId().equals(restaurantId)) {
                isFavorited = true;
                break;
            }
        }

        if (isFavorited) {
            favorites.removeIf(favorite -> favorite.getId().equals(restaurantId));
        } else {
            favorites.add(dto);
        }

        userRepository.save(user);
        return dto;
    }

    @Override
    public Restaurant updateRestaurantStatus(String restaurantId) throws Exception {
        Restaurant restaurant = findRestaurantById(restaurantId);
        restaurant.setOpen(!restaurant.isOpen());
        return restaurantRepository.save(restaurant);
    }
}
