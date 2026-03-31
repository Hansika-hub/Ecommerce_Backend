package com.example.ecommapp.service;

import com.example.ecommapp.dto.AuthResponse;
import com.example.ecommapp.dto.LoginRequest;
import com.example.ecommapp.dto.RegisterRequest;
import com.example.ecommapp.model.User;
import com.example.ecommapp.repository.commerce.UserRepository;
import com.example.ecommapp.security.JwtUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtil jwtUtil;

    public String register(RegisterRequest request) {

        if (userRepository.existsByEmail(request.getEmail())) {
            throw new RuntimeException("Email already exists");
        }

        User user = new User();
        user.setEmail(request.getEmail());
        user.setPasswordHash(passwordEncoder.encode(request.getPassword())); // bcrypt
        user.setName(request.getName());
        user.setPhone(request.getPhone());
        user.setAddresses(request.getAddresses() != null ? request.getAddresses() : new ArrayList<>());
        if ("admin@test.com".equals(request.getEmail())) {
            user.setRoles(List.of("ROLE_CUSTOMER", "ROLE_ADMIN"));
        } else {
            user.setRoles(List.of("ROLE_CUSTOMER"));
        }
        user.setCreatedAt(LocalDateTime.now());

        userRepository.save(user);

        return "User registered successfully";
    }

    public AuthResponse login(LoginRequest request) {

        User user = userRepository.findByEmail(request.getEmail())
                .orElseThrow(() -> new RuntimeException("User not found"));

        if (!passwordEncoder.matches(request.getPassword(), user.getPasswordHash())) {
            throw new RuntimeException("Invalid password");
        }

        String token = jwtUtil.generateToken(user.getEmail(), user.getRoles());

        return new AuthResponse(token, user.getEmail(), user.getRoles());
    }
}