package com.MDM.demo.controller;

import java.util.Optional;
import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.MDM.demo.entity.User;
import com.MDM.demo.repository.UserRepository;

@RestController
@RequestMapping("/api")
@CrossOrigin(origins = "http://localhost:3000")
public class UserController {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @PostMapping("/register")
    public ResponseEntity<String> registerUser(@RequestBody User user) {
        try {
            if (user.getPersonalNumber() == null || user.getPersonalNumber().isEmpty()) {
                Random random = new Random();
                user.setPersonalNumber(String.format("%012d", random.nextInt(1_000_000_000)));
            }
            Optional<User> existingUser = userRepository.findByEmail(user.getEmail());
            if (existingUser.isPresent()) {
                return ResponseEntity.status(HttpStatus.CONFLICT).body("user with this email is exists");
            }

            if (user.getPasswordHash() == null || user.getPasswordHash().isEmpty()) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("password must be filled");
            }

            String encodedPassword = passwordEncoder.encode(user.getPasswordHash());
            user.setPasswordHash(encodedPassword);
            user.setRole("USER");
            userRepository.save(user);

            return ResponseEntity.ok("succesfuly resistered");
        } catch (Exception e) {
            e.printStackTrace(); 
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Registration error");
        }
    }
}
