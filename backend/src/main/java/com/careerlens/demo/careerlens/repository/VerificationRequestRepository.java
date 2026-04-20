package com.careerlens.demo.careerlens.repository;

import com.careerlens.demo.careerlens.entity.VerificationRequest;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface VerificationRequestRepository extends JpaRepository<VerificationRequest, Long> {
    List<VerificationRequest> findByUserId(Long userId);
    List<VerificationRequest> findByStatus(com.careerlens.demo.careerlens.entity.Enums.VerificationStatus status);
}
