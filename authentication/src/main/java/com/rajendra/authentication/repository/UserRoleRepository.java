package com.rajendra.authentication.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.rajendra.authentication.entity.UserRole;
import java.util.List;

public interface UserRoleRepository extends JpaRepository<UserRole, Long> {
    
    List<UserRole> findByUserId(Long userId);
    
    List<UserRole> findByRoleId(Long roleId);
}
