package com.abednego.oauthjwt.repo;

import com.abednego.oauthjwt.model.Users;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface UserRepo extends JpaRepository<Users, Integer> {
    Users findByUsername(String username);
    Users findByEmail(String email);
}
