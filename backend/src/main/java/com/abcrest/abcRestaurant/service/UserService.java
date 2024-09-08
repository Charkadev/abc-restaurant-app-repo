package com.abcrest.abcRestaurant.service;

import com.abcrest.abcRestaurant.model.User;

public interface UserService {

    public User findUserByJwtToken(String jwt) throws Exception;
    public User findUserByEmail(String email) throws Exception;
}
