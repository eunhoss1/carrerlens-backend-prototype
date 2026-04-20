package com.careerlens.demo.careerlens.repository;

import com.careerlens.demo.careerlens.entity.VerificationBadge;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface VerificationBadgeRepository extends JpaRepository<VerificationBadge, Long> {
    java.util.Optional<VerificationBadge> findByUserId(Long userId);
}
