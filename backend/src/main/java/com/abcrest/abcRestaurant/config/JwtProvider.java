package com.abcrest.abcRestaurant.config;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.util.Collection;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

@Service
public class JwtProvider {

    // Using the secret key for HMAC-SHA-256 signing
    private SecretKey key = Keys.hmacShaKeyFor(JwtConstant.SECRET_KEY.getBytes());

    // Generate a JWT token with email and authorities (roles)
    public String generateToken(Authentication auth) {
        // Get user roles (authorities)
        Collection<? extends GrantedAuthority> authorities = auth.getAuthorities();
        String roles = populateAuthorities(authorities);

        // Build the JWT token with email and authorities
        String jwt = Jwts.builder().setIssuedAt(new Date())
                .setExpiration(new Date(new Date().getTime() + 86400000))  // Token validity: 1 day
                .claim("email", auth.getName())
                .claim("authorities", roles)  // Authorities represent roles (e.g., ROLE_ADMIN, ROLE_RESTAURANT_STAFF)
                .signWith(key)
                .compact();

        return jwt;
    }

    // Extract the email from the JWT token
    public String getEmailFromJwtToken(String jwt) {
        jwt = jwt.substring(7);  // Remove 'Bearer ' prefix if present
        Claims claims = Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(jwt).getBody();
        return claims.get("email", String.class);  // Return the email from the JWT
    }

    // Helper method to convert authorities to a comma-separated string
    private String populateAuthorities(Collection<? extends GrantedAuthority> authorities) {
        Set<String> auths = new HashSet<>();
        for (GrantedAuthority authority : authorities) {
            auths.add(authority.getAuthority());  // Add each authority (role)
        }
        return String.join(",", auths);  // Return roles as a comma-separated string
    }
}
