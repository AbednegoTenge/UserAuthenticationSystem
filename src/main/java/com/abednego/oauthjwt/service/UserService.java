package com.abednego.oauthjwt.service;

import com.abednego.oauthjwt.model.Users;
import com.abednego.oauthjwt.repo.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;


@Service
public class UserService {
    private final UserRepo repo;

    private final BCryptPasswordEncoder encoder = new BCryptPasswordEncoder(12);

    private final AuthenticationManager authenticationManager;

    private final JWTService jwtService;


    @Autowired
    public UserService(UserRepo repo, AuthenticationManager authenticationManager, JWTService jwtService) {
        this.repo = repo;
        this.authenticationManager = authenticationManager;
        this.jwtService = jwtService;
    }

    public Users register(Users user) {
        Users existingUser = repo.findByEmail(user.getEmail());
        if (existingUser != null) {
            throw new RuntimeException("Email already in use");
        }
        user.setPassword(encoder.encode(user.getPassword()));
        return repo.save(user);
    }

    public String verifyUser(Users user) {
        Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
                user.getEmail(), user.getPassword()));
        if (authentication.isAuthenticated()) {
            return jwtService.generateToken(user.getEmail());
        }
        return "Invalid username or password";
    }
}
