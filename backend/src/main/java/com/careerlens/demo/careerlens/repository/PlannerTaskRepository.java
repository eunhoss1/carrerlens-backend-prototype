package com.careerlens.demo.careerlens.repository;

import com.careerlens.demo.careerlens.entity.PlannerTask;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PlannerTaskRepository extends JpaRepository<PlannerTask, Long> {
    List<PlannerTask> findByRoadmapId(Long roadmapId);
}
