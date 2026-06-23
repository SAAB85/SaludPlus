package com.ejemplo.saludplus.service;

import com.ejemplo.saludplus.model.User;
import com.ejemplo.saludplus.repository.UserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    private final UserRepository repository;
    private final PasswordEncoder passwordEncoder;

    public UserService(UserRepository repository, PasswordEncoder passwordEncoder) {
        this.repository = repository;
        this.passwordEncoder = passwordEncoder;
    }

    public User findByUsername(String username) {
        return repository.findByUsername(username).orElseThrow();
    }

    public User save(User user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        return repository.save(user);
    }
}