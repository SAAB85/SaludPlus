package com.ejemplo.saludplus.controller;

import com.ejemplo.saludplus.dto.AuthResponse;
import com.ejemplo.saludplus.dto.LoginRequest;
import com.ejemplo.saludplus.model.User;
import com.ejemplo.saludplus.security.JwtService;
import com.ejemplo.saludplus.service.UserService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
public class AuthController {

    private final UserService userService;
    private final JwtService jwtService;
    private final PasswordEncoder passwordEncoder;

    public AuthController(UserService userService, JwtService jwtService, PasswordEncoder passwordEncoder) {
        this.userService = userService;
        this.jwtService = jwtService;
        this.passwordEncoder = passwordEncoder;
    }

    @PostMapping("/login")
    public AuthResponse login(@RequestBody LoginRequest request) {

        User user = userService.findByUsername(request.getUsername());

        if (!passwordEncoder.matches(request.getPassword(), user.getPassword())) {
            throw new RuntimeException("Credenciales inválidas");
        }

        String token = jwtService.generateToken(user.getUsername());

        return new AuthResponse(token);
    }
}