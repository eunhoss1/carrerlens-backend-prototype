package com.careerlens.demo.careerlens.repository;

import com.careerlens.demo.careerlens.entity.PatternProfile;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PatternProfileRepository extends JpaRepository<PatternProfile, Long> {
}
