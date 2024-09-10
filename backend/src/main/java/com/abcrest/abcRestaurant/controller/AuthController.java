package com.abcrest.abcRestaurant.controller;

import com.abcrest.abcRestaurant.config.JwtProvider;
import com.abcrest.abcRestaurant.model.Cart;
import com.abcrest.abcRestaurant.model.USER_ROLE;
import com.abcrest.abcRestaurant.model.User;
import com.abcrest.abcRestaurant.repository.CartRepository;
import com.abcrest.abcRestaurant.repository.UserRepository;
import com.abcrest.abcRestaurant.request.LoginRequest;
import com.abcrest.abcRestaurant.response.AuthResponse;
import com.abcrest.abcRestaurant.service.CustomerUserDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collection;

@RestController
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private JwtProvider jwtProvider;

    @Autowired
    private CustomerUserDetailsService customerUserDetailsService;

    @Autowired
    private CartRepository cartRepository;

    @PostMapping("/signup")
    public ResponseEntity<?> createUserHandler(@RequestBody User user) {
        User isEmailExist = userRepository.findByEmail(user.getEmail());
        if (isEmailExist != null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body("Email is already in use with another account");
        }

        // Hash the password before saving the user
        User createdUser = new User();
        createdUser.setEmail(user.getEmail());
        createdUser.setFullName(user.getFullName());
        createdUser.setRole(user.getRole());
        createdUser.setPassword(passwordEncoder.encode(user.getPassword()));

        User savedUser = userRepository.save(createdUser);

        // Create a cart for the user
        Cart cart = new Cart();
        cart.setCustomer(savedUser);
        cartRepository.save(cart);

        // Authenticate the newly created user
        Authentication authentication = new UsernamePasswordAuthenticationToken(user.getEmail(), user.getPassword());
        SecurityContextHolder.getContext().setAuthentication(authentication);

        // Generate JWT token
        String jwt = jwtProvider.generateToken(authentication);

        // Create the response object
        AuthResponse authResponse = new AuthResponse();
        authResponse.setJwt(jwt);
        authResponse.setMessage("Register success");
        authResponse.setRole(savedUser.getRole());

        return new ResponseEntity<>(authResponse, HttpStatus.CREATED);
    }

    @PostMapping("/signin")
    public ResponseEntity<AuthResponse> signin(@RequestBody LoginRequest req) {
        String username = req.getEmail();
        String password = req.getPassword();

        // Authenticate the user
        Authentication authentication = authenticate(username, password);

        // Extract the role from authorities
        Collection<? extends GrantedAuthority> authorities = authentication.getAuthorities();
        String role = authorities.isEmpty() ? null : authorities.iterator().next().getAuthority();

        System.out.println("Extracted role from authorities: " + role);

        // Don't remove the "ROLE_" prefix from the role
        System.out.println("Role being used: " + role);

        // Generate JWT token
        String jwt = jwtProvider.generateToken(authentication);

        // Create the response object
        AuthResponse authResponse = new AuthResponse();
        authResponse.setJwt(jwt);
        authResponse.setMessage("Login success");
        authResponse.setRole(USER_ROLE.valueOf(role));  // Match with USER_ROLE enum

        return new ResponseEntity<>(authResponse, HttpStatus.OK);
    }

    private Authentication authenticate(String username, String password) {
        UserDetails userDetails = customerUserDetailsService.loadUserByUsername(username);

        if (userDetails == null) {
            System.out.println("User not found.");
            throw new BadCredentialsException("Invalid username...");
        }

        // Log the hashed password and entered password
        System.out.println("Hashed password in DB: " + userDetails.getPassword());
        System.out.println("Entered password: " + password);

        // Check if the password matches the hashed password
        if (!passwordEncoder.matches(password, userDetails.getPassword())) {
            System.out.println("Password does not match.");
            throw new BadCredentialsException("Invalid password...");
        }

        return new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
    }
}
