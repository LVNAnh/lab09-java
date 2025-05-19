package com.example.webservice.service;

import com.example.webservice.dto.LoginRequest;
import com.example.webservice.dto.RegisterRequest;
import com.example.webservice.exception.BadRequestException;
import com.example.webservice.model.User;
import com.example.webservice.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserService implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    @Lazy
    private AuthenticationManager authenticationManager;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        try {
            System.out.println("Attempting to load user with email: " + email);
            User user = userRepository.findByEmail(email)
                    .orElseThrow(() -> {
                        System.out.println("User not found with email: " + email);
                        return new UsernameNotFoundException("User not found with email: " + email);
                    });

            System.out.println("User found with email: " + email);
            System.out.println("Password from DB: " + user.getPassword());

            return org.springframework.security.core.userdetails.User
                    .withUsername(user.getEmail())
                    .password(user.getPassword())
                    .authorities("USER")
                    .build();
        } catch (Exception e) {
            System.out.println("Error in loadUserByUsername: " + e.getMessage());
            e.printStackTrace();
            throw e;
        }
    }

    // Method for registering a new user
    public User register(RegisterRequest registerRequest) {
        if (userRepository.existsByEmail(registerRequest.getEmail())) {
            throw new BadRequestException("Email is already taken!");
        }

        User user = new User();
        user.setEmail(registerRequest.getEmail());
        user.setPassword(passwordEncoder.encode(registerRequest.getPassword()));
        user.setFirstName(registerRequest.getFirstName());
        user.setLastName(registerRequest.getLastName());

        return userRepository.save(user);
    }

    // Method for authentication
    public Authentication authenticate(LoginRequest loginRequest) {
        try {
            System.out.println("Attempting to authenticate user with email: " + loginRequest.getEmail());

            // Create the authentication token
            UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(
                    loginRequest.getEmail(), loginRequest.getPassword());

            try {
                // Attempt authentication
                Authentication authentication = authenticationManager.authenticate(authToken);
                SecurityContextHolder.getContext().setAuthentication(authentication);
                System.out.println("Authentication successful for user: " + loginRequest.getEmail());
                return authentication;
            } catch (Exception e) {
                System.out.println("Authentication failed for user: " + loginRequest.getEmail());
                System.out.println("Authentication error: " + e.getMessage());
                e.printStackTrace();
                throw e;
            }
        } catch (Exception e) {
            System.out.println("Error in authenticate method: " + e.getMessage());
            e.printStackTrace();
            throw e;
        }
    }

    // Get current authenticated user
    public User getCurrentUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String email = authentication.getName();
        return userRepository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("User not found with email: " + email));
    }

    // Helper method to get user by email
    public User getUserByEmail(String email) {
        return userRepository.findByEmail(email)
                .orElse(null);
    }
}