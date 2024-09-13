package com.abcrest.abcRestaurant.config;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.crypto.SecretKey;
import java.io.IOException;
import java.util.List;

public class JwtTokenValidator extends OncePerRequestFilter {

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String jwt = request.getHeader(JwtConstant.JWT_HEADER);  // Get the JWT token from the header
        System.out.println("JWT Token: " + jwt);  // Log the JWT token

        if (jwt != null && jwt.startsWith("Bearer ")) {  // Check if the token is present and valid
            jwt = jwt.substring(7);  // Remove 'Bearer ' prefix

            try {
                SecretKey key = Keys.hmacShaKeyFor(JwtConstant.SECRET_KEY.getBytes());
                Claims claims = Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(jwt).getBody();

                String email = String.valueOf(claims.get("email"));  // Extract email from claims
                String authorities = String.valueOf(claims.get("authorities"));  // Extract roles from claims
                System.out.println("JWT Email: " + email);  // Log email for debugging
                System.out.println("JWT Authorities: " + authorities);  // Log authorities for debugging

                // Convert authorities (roles) to GrantedAuthority and set authentication context
                List<GrantedAuthority> auth = AuthorityUtils.commaSeparatedStringToAuthorityList(authorities);
                Authentication authentication = new UsernamePasswordAuthenticationToken(email, null, auth);
                SecurityContextHolder.getContext().setAuthentication(authentication);  // Set authentication
            } catch (Exception e) {
                e.printStackTrace();  // Log the exception
                throw new BadCredentialsException("Invalid token");  // Handle invalid token scenario
            }
        }

        filterChain.doFilter(request, response);  // Continue the filter chain
    }
}
