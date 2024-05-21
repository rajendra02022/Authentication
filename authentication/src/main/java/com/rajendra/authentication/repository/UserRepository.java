package com.rajendra.authentication.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import com.rajendra.authentication.entity.User;
import java.util.Optional;
import java.util.List;
import java.time.LocalDate;


public interface UserRepository extends JpaRepository<User, Long> {
    
    boolean existsByUsername(String username);
    
    Optional<User> findByUsername(String username);

    @Override
    <S extends User> S save(S entity);

    Optional<User> findByEmail(String email);
    
    void deleteByUsername(String username);
    
    // Find users by role
    List<User> findByRoles_Name(String roleName); // Adjusted method to query by role name
    
}
