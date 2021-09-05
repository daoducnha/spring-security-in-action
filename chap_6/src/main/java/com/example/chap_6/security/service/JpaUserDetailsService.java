package com.example.chap_6.security.service;

import com.example.chap_6.entity.User;
import com.example.chap_6.repository.UserRepository;
import com.example.chap_6.security.model.CustomUserDetails;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.function.Supplier;

@Service
public class JpaUserDetailsService implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    @Override
    public CustomUserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Supplier<UsernameNotFoundException> s = () -> new UsernameNotFoundException("Problem during authentication!");
        User u = userRepository
                .findUserByUsername(username)
                .orElseThrow(s);
        return new CustomUserDetails(u);
    }
}
