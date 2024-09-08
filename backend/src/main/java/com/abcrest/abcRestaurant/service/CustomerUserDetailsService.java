package com.abcrest.abcRestaurant.service;

import com.abcrest.abcRestaurant.model.USER_ROLE;
import com.abcrest.abcRestaurant.model.User;
import com.abcrest.abcRestaurant.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class CustomerUserDetailsService implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        // Retrieve the user by email from the UserRepository
        User user = userRepository.findByEmail(username);

        // If user is not found, throw an exception
        if (user == null) {
            throw new UsernameNotFoundException("User not found with email: " + username);
        }

        // Get the role of the user (from USER_ROLE enum)
        USER_ROLE role = user.getRole();

        // Convert the role to a GrantedAuthority for Spring Security
        List<GrantedAuthority> authorities = new ArrayList<>();
        authorities.add(new SimpleGrantedAuthority(role.toString()));  // Adds the user's role (e.g., ROLE_CUSTOMER, ROLE_ADMIN)

        // Return UserDetails object for Spring Security
        return new org.springframework.security.core.userdetails.User(user.getEmail(), user.getPassword(), authorities);
    }
}
