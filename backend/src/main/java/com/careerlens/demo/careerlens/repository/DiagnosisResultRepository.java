package com.careerlens.demo.careerlens.repository;

import com.careerlens.demo.careerlens.entity.DiagnosisResult;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface DiagnosisResultRepository extends JpaRepository<DiagnosisResult, Long> {
    List<DiagnosisResult> findByUserId(Long userId);
}
