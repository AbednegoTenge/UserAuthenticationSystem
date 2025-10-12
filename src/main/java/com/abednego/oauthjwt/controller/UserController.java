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
    public ResponseEntity<Users> login(@RequestBody Users user) {
        Users existingUser = service.verifyUser(user);
        return ResponseEntity.status(HttpStatus.OK).body(user);
    }
}
