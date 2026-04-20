package com.careerlens.demo.careerlens.repository;

import com.careerlens.demo.careerlens.entity.JobPosting;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface JobPostingRepository extends JpaRepository<JobPosting, Long> {
}
