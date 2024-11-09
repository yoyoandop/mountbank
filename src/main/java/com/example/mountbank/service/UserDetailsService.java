package com.example.mountbank.service;

import com.example.mountbank.model.User;
import com.example.mountbank.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.core.userdetails.User.UserBuilder;
import org.springframework.stereotype.Service;

@Service
public class UserDetailsService implements org.springframework.security.core.userdetails.UserDetailsService {

    private final UserRepository userRepository;

    @Autowired
    public UserDetailsService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String phoneNumber) throws UsernameNotFoundException {
        // Using phoneNumber to fetch user from the repository
        User user = userRepository.findByPhoneNumber(phoneNumber)
                .orElseThrow(() -> new UsernameNotFoundException("User not found with phone number: " + phoneNumber));

        // Build and return UserDetails object
        UserBuilder userBuilder = org.springframework.security.core.userdetails.User.withUsername(user.getPhoneNumber());
        userBuilder.password(user.getPassword());
        userBuilder.roles("USER"); // Assuming a user role
        return userBuilder.build();
    }
}
