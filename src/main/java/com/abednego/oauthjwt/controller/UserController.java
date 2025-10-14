package com.abednego.oauthjwt.controller;

import com.abednego.oauthjwt.model.Users;
import com.abednego.oauthjwt.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
public class UserController {
    private final UserService service;

    @Autowired
    public UserController(UserService service) {
        this.service = service;
    }

    @GetMapping("/")
    public String home() {
        return "Welcome to the Home Page!";
    }

    @PostMapping("/register")
    public ResponseEntity<Users> register(@RequestBody Users user) {
        Users newUser = service.register(user);
        return ResponseEntity.status(HttpStatus.CREATED).body(newUser);
    }

    @PostMapping("/login")
    public ResponseEntity<Map<String, String>> login(@RequestBody Users user) {
        String token = service.verifyUser(user);

        if (token != null && !token.equals("Invalid username or password")) {
            Map<String, String> response = new HashMap<>();
            response.put("token", token);
            return ResponseEntity.ok(response);
        }

        return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                .body(Map.of("error", "Invalid username or password"));
    }
}
