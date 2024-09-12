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
                        // Allow public access to restaurants page
                        .requestMatchers("/api/restaurants/**").permitAll()  // <-- Public access to restaurant APIs
                        // Permit access to food search and menu items without authentication
                        .requestMatchers("/api/food/**").permitAll()
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
                .cors(cors -> cors.configurationSource(corsConfigurationSource()));

        return http.build();
    }

    private CorsConfigurationSource corsConfigurationSource() {
        return new CorsConfigurationSource() {
            @Override
            public CorsConfiguration getCorsConfiguration(HttpServletRequest request) {
                CorsConfiguration cfg = new CorsConfiguration();
                cfg.setAllowedOrigins(Arrays.asList("http://localhost:3000"));  // Allow correct origin (non-SSL)
                cfg.setAllowedMethods(Collections.singletonList("*"));  // Allow all methods (GET, POST, etc.)
                cfg.setAllowCredentials(true);
                cfg.setAllowedHeaders(Collections.singletonList("*"));  // Allow all headers
                cfg.setExposedHeaders(Arrays.asList("Authorization"));  // Expose Authorization header
                cfg.setMaxAge(3600L);  // Cache CORS configuration for 1 hour
                return cfg;
            }
        };
    }

    @Bean
    PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
