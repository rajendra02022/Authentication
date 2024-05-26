package com.rajendra.authentication.repository;

import com.rajendra.authentication.entity.Profile;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProfileRepository extends JpaRepository<Profile, Long> {
    Profile findByUserId(Long userId);
    void deleteByUserId(Long userId);
}
