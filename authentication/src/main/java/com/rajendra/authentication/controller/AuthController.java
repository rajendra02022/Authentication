package com.rajendra.authentication.controller;

import com.rajendra.authentication.entity.User;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import com.rajendra.authentication.dto.LoginRequest;
import com.rajendra.authentication.dto.RegisterRequest;
import com.rajendra.authentication.dto.AuthResponse;
import com.rajendra.authentication.service.AuthService;

  
import lombok.extern.slf4j.Slf4j;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;

@RestController
@RequestMapping("/api/auth")
@Slf4j
public class AuthController {

    private final AuthenticationManager authenticationManager;
    private final AuthService authService;

    @Autowired
    public AuthController(AuthenticationManager authenticationManager, AuthService authService) {
        this.authenticationManager = authenticationManager;
        this.authService = authService;
    }

    @Operation(summary = "Authenticate user and return token")
    @ApiResponse(responseCode = "200", description = "User authenticated successfully", content = @Content(schema = @Schema(implementation = AuthResponse.class)))
    @ApiResponse(responseCode = "401", description = "Invalid credentials", content = @Content(schema = @Schema(implementation = AuthResponse.class)))
    @ApiResponse(responseCode = "500", description = "Internal server error", content = @Content(schema = @Schema(implementation = AuthResponse.class)))
    @PostMapping("/login")
    public ResponseEntity<AuthResponse> authenticateUser(@Valid @RequestBody LoginRequest loginRequest) {
        log.info("Authenticating user: {}", loginRequest.username());
        var authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        loginRequest.username(),
                        loginRequest.password()
                )
        );

        SecurityContextHolder.getContext().setAuthentication(authentication);
        var token = authService.generateToken(authentication);
        return ResponseEntity.ok(new AuthResponse("User authenticated successfully", token));
    }

    @Operation(summary = "Register a new user")
    @ApiResponse(responseCode = "201", description = "User registered successfully", content = @Content(schema = @Schema(implementation = AuthResponse.class)))
    @ApiResponse(responseCode = "409", description = "Username is already taken", content = @Content(schema = @Schema(implementation = AuthResponse.class)))
    @ApiResponse(responseCode = "500", description = "Internal server error", content = @Content(schema = @Schema(implementation = AuthResponse.class)))
    @PostMapping("/register")
    @Validated
    public ResponseEntity<AuthResponse> registerUser(@Valid @RequestBody RegisterRequest registerRequest) {
        log.info("Registering user: {} , email: {}", registerRequest.username(),registerRequest.email());
        if (authService.userExists(registerRequest.username())) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(new AuthResponse("Username is already taken ", null));
        }

        authService.registerUser(registerRequest);
        return ResponseEntity.status(HttpStatus.CREATED).body(new AuthResponse("User registered successfully", null));
    }

@Operation(summary = "Get authenticated user details")
@ApiResponse(responseCode = "200", description = "User details retrieved successfully", content = @Content(schema = @Schema(implementation = User.class)))
@ApiResponse(responseCode = "401", description = "Unauthorized", content = @Content)
@GetMapping("/profile")
public ResponseEntity<User> getUserProfile(@RequestHeader("Authorization") String token) {
    if (token != null && token.startsWith("Bearer ")) {
        token = token.substring(7);
        if (authService.validateToken(token)) {
            String username = authService.getUsernameFromToken(token);
            User user = authService.getUserByUsername(username);
            return ResponseEntity.ok(user);
        }
    }
    return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
}
}
