package com.rajendra.authentication.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.rajendra.authentication.dto.RegisterRequest;
import com.rajendra.authentication.entity.User;
import com.rajendra.authentication.repository.UserRepository;
import com.rajendra.authentication.security.JwtProvider;
import com.rajendra.authentication.service.RoleService;
import com.rajendra.authentication.service.UserRoleService;

@Service
public class AuthService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtProvider jwtProvider;
    private final RoleService roleService;
    private final UserRoleService userRoleService;

    @Autowired
    public AuthService(UserRepository userRepository, PasswordEncoder passwordEncoder, JwtProvider jwtProvider, RoleService roleService, UserRoleService userRoleService) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtProvider = jwtProvider;
        this.roleService = roleService;
        this.userRoleService = userRoleService;
    }

    @Transactional
    public void registerUser(RegisterRequest registerRequest) {
        User user = new User();
        user.setUsername(registerRequest.username());
        user.setPassword(passwordEncoder.encode(registerRequest.password()));
        user.setEmail(registerRequest.email());
        userRepository.save(user);
        // Assign roles to user
    }

    public boolean userExists(String username) {
        return userRepository.existsByUsername(username);
    }

    public String generateToken(Authentication authentication) {
        return jwtProvider.generateToken(authentication);
    }

    public boolean validateToken(String token) {
        return jwtProvider.validateToken(token);
    }
    
    public String getUsernameFromToken(String token) {
        return jwtProvider.getUsernameFromToken(token);
    }
    
    public User getUserByUsername(String username) {
        return userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("User not found with username: " + username));
    }
}

