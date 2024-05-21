package com.rajendra.authentication.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.rajendra.authentication.entity.Role;
import java.util.Optional;

public interface RoleRepository extends JpaRepository<Role, Long> {
    
    Optional<Role> findByName(String name);
}
