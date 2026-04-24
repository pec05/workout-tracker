package com.peccio.workout_tracker.service;

import com.peccio.workout_tracker.dto.AuthResponse;
import com.peccio.workout_tracker.dto.LoginRequest;
import com.peccio.workout_tracker.dto.RegisterRequest;
import com.peccio.workout_tracker.entity.Role;
import com.peccio.workout_tracker.entity.User;
import com.peccio.workout_tracker.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;


    /*
            Build a User object from the request
    Hash the password
    Save to DB
    Generate and return a JWT
             */
    public AuthResponse register(RegisterRequest request) {
        var user = new User();
        user.setEmail(request.getEmail());
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        user.setUsername(request.getUsername());
        user.setRole(Role.USER);
        user.setCreatedAt(LocalDateTime.now());
        userRepository.save(user);

        var jwtToken = jwtService.generateToken(user.getEmail());
        return new AuthResponse(jwtToken);
    }

    public AuthResponse login(LoginRequest request) {
        // Authenticate the user (this will throw an exception if authentication fails)
        authenticationManager.authenticate(
                new org.springframework.security.authentication.UsernamePasswordAuthenticationToken(
                        request.getEmail(),
                        request.getPassword()
                )
        );

        // If authentication is successful, generate a JWT
        var jwtToken = jwtService.generateToken(request.getEmail());
        return new AuthResponse(jwtToken);
    }
}
