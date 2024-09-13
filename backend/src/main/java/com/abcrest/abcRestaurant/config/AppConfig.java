package com.abcrest.abcRestaurant.config;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;

import java.util.Arrays;
import java.util.Collections;

@Configuration
@EnableWebSecurity
public class AppConfig {

    @Bean
    SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {

        http.sessionManagement(management -> management.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authorizeHttpRequests(authorize -> authorize
                        // Permit access to register and login endpoints without authentication
                        .requestMatchers("/auth/signup", "/auth/login").permitAll()
                        // Allow public access to restaurants page and food APIs without authentication
                        .requestMatchers("/api/restaurants/**", "/api/food/**").permitAll()
                        // Only users with Admin role can access /api/admin/**
                        .requestMatchers("/api/admin/**").hasRole("ADMIN")
                        // Restaurant staff and Admins can access /api/staff/**
                        .requestMatchers("/api/staff/**").hasAnyRole("RESTAURANT_STAFF", "ADMIN")
                        // Allow staff and admin to access all reservations
                        .requestMatchers("/api/reservations/all").hasAnyRole("RESTAURANT_STAFF", "ADMIN")
                        // Allow authenticated users to create a reservation
                        .requestMatchers("/api/reservations").authenticated()
                        // All other authenticated requests for /api/**
                        .requestMatchers("/api/**").authenticated()
                        // All other requests are allowed without authentication
                        .anyRequest().permitAll()
                )
                .addFilterBefore(new JwtTokenValidator(), BasicAuthenticationFilter.class)
                .csrf(csrf -> csrf.disable())  // Disable CSRF for API calls
                .cors(cors -> cors.configurationSource(corsConfigurationSource()));  // Enable CORS

        return http.build();
    }

    // CORS Configuration to allow frontend access
    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        return request -> {
            CorsConfiguration cfg = new CorsConfiguration();
            cfg.setAllowedOrigins(Collections.singletonList("http://localhost:3000"));  // Frontend origin
            cfg.setAllowedMethods(Arrays.asList("GET", "POST", "PUT", "DELETE"));  // Allow common methods
            cfg.setAllowCredentials(true);  // Allow credentials (JWT tokens, etc.)
            cfg.setAllowedHeaders(Arrays.asList("Authorization", "Content-Type"));  // Allow necessary headers
            cfg.setExposedHeaders(Arrays.asList("Authorization"));  // Expose Authorization header to frontend
            return cfg;
        };
    }

    @Bean
    PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();  // BCrypt for secure password storage
    }
}
