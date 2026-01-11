package com.tooltechind.config;

import com.tooltechind.util.JwtUtil;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {
    
    @Autowired
    private JwtUtil jwtUtil;
    
    @Autowired
    private UserDetailsService userDetailsService;
    
    @Override
    protected void doFilterInternal(HttpServletRequest request, 
                                  HttpServletResponse response, 
                                  FilterChain filterChain) throws ServletException, IOException {
        
        String authHeader = request.getHeader("Authorization");
        System.out.println("JWT Filter - Header: " + authHeader);  // DEBUG
        
        String token = null;
        String email = null;
        
        if (authHeader != null && authHeader.startsWith("Bearer ")) {
            token = authHeader.substring(7);
            try {
                email = jwtUtil.extractEmail(token);
                String tokenRole = jwtUtil.extractRole(token);
                System.out.println("JWT - Email: " + email + ", Role: " + tokenRole);  // DEBUG
                
                if (email != null && SecurityContextHolder.getContext().getAuthentication() == null) {
                    UserDetails userDetails = userDetailsService.loadUserByUsername(email);
                    
                    // FIXED: Compare "ADMIN" vs "ROLE_ADMIN"
                    String userRole = userDetails.getAuthorities().iterator().next().getAuthority();
                    if (userRole.equals("ROLE_" + tokenRole)) {  // ← FIXED!
                        UsernamePasswordAuthenticationToken authToken = 
                            new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
                        authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                        SecurityContextHolder.getContext().setAuthentication(authToken);
                        System.out.println("JWT Auth success: " + email);  // DEBUG
                    } else {
                        System.out.println("JWT Role mismatch: " + tokenRole + " vs " + userRole);
                    }
                }
            } catch (Exception e) {
                System.out.println("JWT Parse error: " + e.getMessage());
            }
        }
        
        filterChain.doFilter(request, response);
    }
}
