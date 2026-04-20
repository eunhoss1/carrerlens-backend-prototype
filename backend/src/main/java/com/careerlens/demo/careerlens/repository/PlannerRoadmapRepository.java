package com.careerlens.demo.careerlens.repository;

import com.careerlens.demo.careerlens.entity.PlannerRoadmap;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PlannerRoadmapRepository extends JpaRepository<PlannerRoadmap, Long> {
    List<PlannerRoadmap> findByUserId(Long userId);
}
