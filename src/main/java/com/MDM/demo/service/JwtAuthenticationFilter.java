package com.MDM.demo.service;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import com.MDM.demo.entity.User;
import com.MDM.demo.repository.UserRepository;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    private UserRepository userRepository;

    @Override
    protected void doFilterInternal(HttpServletRequest request,
    HttpServletResponse response,
    FilterChain filterChain)
   throws ServletException, IOException {
        final String authHeader = request.getHeader("Authorization");
        String jwt = null;
        String userEmail = null;

        if (authHeader != null && authHeader.startsWith("Bearer ")) {
            jwt = authHeader.substring(7);
            try {
                userEmail = jwtUtil.extractUsername(jwt);
            } catch (Exception e) {
                System.err.println("error with extract username from  JWT: " + e.getMessage());
            }
        }

     
        if (userEmail != null && SecurityContextHolder.getContext().getAuthentication() == null) {
           
            User user = userRepository.findByEmail(userEmail).orElse(null);
            if (user != null && jwtUtil.validateToken(jwt, user)) {
              
                UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(
    user,
    null,
    user.getAuthorities()
                );
                authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                //
                SecurityContextHolder.getContext().setAuthentication(authToken);
            }
        }

       
        filterChain.doFilter(request, response);
    }
}
