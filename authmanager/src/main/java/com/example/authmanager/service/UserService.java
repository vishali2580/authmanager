package com.example.authmanager.service;

import com.example.authmanager.model.User;
import com.example.authmanager.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    private final BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    public User registerUser(String email, String rawPassword, String fullName) {
        String hashedPassword = passwordEncoder.encode(rawPassword);
        User newUser = new User(email, hashedPassword, fullName);
        return userRepository.save(newUser);
    }

    public boolean checkLogin(String email, String rawPassword) {
        Optional<User> userOptional = userRepository.findByEmail(email);

        if (userOptional.isEmpty()) {
            return false;
        }

        User user = userOptional.get();
        return passwordEncoder.matches(rawPassword, user.getPassword());
    }
}