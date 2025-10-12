package com.abednego.oauthjwt.service;

import com.abednego.oauthjwt.model.UserPrincipal;
import com.abednego.oauthjwt.model.Users;
import com.abednego.oauthjwt.repo.UserRepo;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;


@Service
public class MyUserDetailsService implements UserDetailsService {

    private final UserRepo repo;

    public MyUserDetailsService(UserRepo repo) {
        this.repo = repo;
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        Users user = repo.findByEmail(email);
        return new UserPrincipal(user);
    }
}
