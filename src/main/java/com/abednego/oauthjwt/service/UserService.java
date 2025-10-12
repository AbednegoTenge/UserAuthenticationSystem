package com.abednego.oauthjwt.service;

import com.abednego.oauthjwt.model.Users;
import com.abednego.oauthjwt.repo.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;


@Service
public class UserService {
    private final UserRepo repo;

    private final BCryptPasswordEncoder encoder = new BCryptPasswordEncoder(12);

    @Autowired
    public UserService(UserRepo repo) {
        this.repo = repo;
    }

    public Users register(Users user) {
        Users existingUser = repo.findByEmail(user.getEmail());
        if (existingUser != null) {
            throw new RuntimeException("Email already in use");
        }
        user.setPassword(encoder.encode(user.getPassword()));
        return repo.save(user);
    }

    public Users verifyUser(Users user) {
        Users existingUser = repo.findByEmail(user.getEmail());
        if (existingUser == null || !encoder.matches(user.getPassword(), existingUser.getPassword())) {
            throw new RuntimeException("Invalid email or password");
        }
        return existingUser;
    }
}
