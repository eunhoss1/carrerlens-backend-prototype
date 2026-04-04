package com.carrerlens.demo.user.repository;

import com.carrerlens.demo.user.entity.UserProfile;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserProfileRepository extends JpaRepository<UserProfile, Long> {
}