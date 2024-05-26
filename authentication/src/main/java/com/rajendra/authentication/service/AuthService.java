package com.rajendra.authentication.service;

import com.rajendra.authentication.dto.RegisterRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.rajendra.authentication.dto.ProfileDTO;
import com.rajendra.authentication.entity.Profile;
import com.rajendra.authentication.entity.Role;
import com.rajendra.authentication.entity.User;
import com.rajendra.authentication.entity.UserRole;
import com.rajendra.authentication.repository.ProfileRepository;
import com.rajendra.authentication.repository.RoleRepository;
import com.rajendra.authentication.repository.UserRepository;
import com.rajendra.authentication.repository.UserRoleRepository;
import com.rajendra.authentication.security.JwtProvider;
import com.rajendra.authentication.service.RoleService;
import com.rajendra.authentication.service.UserRoleService;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
public class AuthService {

    private final UserRepository userRepository;
    private final ProfileRepository profileRepository;
    private final RoleRepository roleRepository;
    private final UserRoleRepository userRoleRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtProvider jwtProvider;
    private final RoleService roleService;
    private final UserRoleService userRoleService;

    @Autowired
    public AuthService(UserRepository userRepository, ProfileRepository profileRepository, RoleRepository roleRepository, UserRoleRepository userRoleRepository, PasswordEncoder passwordEncoder, JwtProvider jwtProvider, RoleService roleService, UserRoleService userRoleService) {
        this.userRepository = userRepository;
        this.profileRepository = profileRepository;
        this.roleRepository = roleRepository;
        this.userRoleRepository = userRoleRepository;
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

        // Assign default role to user
        Optional<Role> roleOptional = roleRepository.findByName("USER");
        Role userRole = roleOptional.orElseThrow(() -> new RuntimeException("Role not found"));
        UserRole userRoleMapping = new UserRole(user, userRole);
        userRoleRepository.save(userRoleMapping);

        // Create a default profile for the new user
        ProfileDTO profileDTO = new ProfileDTO("DefaultFirstName", "DefaultLastName", null, null);
        createProfile(user, profileDTO);
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
    
    public void invalidateToken(String token) {
        jwtProvider.invalidateToken(token);
    }
    
    public String getUsernameFromToken(String token) {
        return jwtProvider.getUsernameFromToken(token);
    }
    
    public User getUserByUsername(String username) {
        return userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("User not found with username: " + username));
    }

    @Transactional
    public Profile createProfile(User user, ProfileDTO profileDTO) {
        Profile profile = new Profile();
        profile.setUser(user);
        profile.setFirstName(profileDTO.firstName());
        profile.setLastName(profileDTO.lastName());
        profile.setPhoneNumber(profileDTO.phoneNumber());
        profile.setAddress(profileDTO.address());
        return profileRepository.save(profile);
    }

    @Transactional
    public Profile updateUserProfile(String username, ProfileDTO updatedProfileDTO) {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("User not found with username: " + username));
        Profile profile = profileRepository.findByUserId(user.getId());
        profile.setFirstName(updatedProfileDTO.firstName());
        profile.setLastName(updatedProfileDTO.lastName());
        profile.setPhoneNumber(updatedProfileDTO.phoneNumber());
        profile.setAddress(updatedProfileDTO.address());
        return profileRepository.save(profile);
    }

    @Transactional(readOnly = true)
    public Profile getProfileByUserId(Long userId) {
        return profileRepository.findByUserId(userId);
    }

    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    @Transactional
    public User updateUser(Long id, User updatedUser) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new UsernameNotFoundException("User not found with id: " + id));
        user.setUsername(updatedUser.getUsername());
        user.setEmail(updatedUser.getEmail());
        user.setPassword(passwordEncoder.encode(updatedUser.getPassword()));
        return userRepository.save(user);
    }

    @Transactional
    public void deleteUser(Long id) {
        profileRepository.deleteByUserId(id);
        userRepository.deleteById(id);
    }

    @Transactional
    public void assignRoleToUser(Long userId, String roleName) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new UsernameNotFoundException("User not found with id: " + userId));
        Role role = roleRepository.findByName(roleName)
                .orElseThrow(() -> new IllegalArgumentException("Role not found: " + roleName));
        UserRole userRole = new UserRole(user, role);
        userRoleRepository.save(userRole);
    }
}

