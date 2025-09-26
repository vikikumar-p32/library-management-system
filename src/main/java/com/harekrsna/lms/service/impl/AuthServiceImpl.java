package com.harekrsna.lms.service.impl;

import com.harekrsna.lms.entity.User;
import com.harekrsna.lms.repository.UserRepository;
import com.harekrsna.lms.security.JwtService;
import com.harekrsna.lms.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;


    @Override
    public User registerNormalUser(User user) {
        if(userRepository.findByUsername(user.getUsername()).isPresent()) {
            throw new RuntimeException("User already registered with username: " + user.getUsername());
        }

        Set<String> roles = new HashSet<>();
        roles.add("ROLE_USER");

        user.setRoles(roles);
        user.setEmail(user.getEmail());
        user.setPassword(passwordEncoder.encode(user.getPassword()));

        return userRepository.save(user);
    }

    @Override
    public User registerAdminUser(User user) {
        if(userRepository.findByUsername(user.getUsername()).isPresent()) {
            throw new RuntimeException("User already registered with username: " + user.getUsername());
        }

        Set<String> roles = new HashSet<>();
        roles.add("ROLE_ADMIN");
        // bcoz an admin is by default also a user
        roles.add("ROLE_USER");

        user.setRoles(roles);
        user.setEmail(user.getEmail());
        user.setPassword(passwordEncoder.encode(user.getPassword()));

        return userRepository.save(user);
    }

    @Override
    public String login(User user) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        user.getUsername(),
                        user.getPassword()
                )
        );

        User existingUser = userRepository.findByUsername(user.getUsername())
                .orElseThrow(() -> new RuntimeException("User not found with username: " + user.getUsername()));

        String token = jwtService.generateToken(existingUser);

        return token;
    }
}
