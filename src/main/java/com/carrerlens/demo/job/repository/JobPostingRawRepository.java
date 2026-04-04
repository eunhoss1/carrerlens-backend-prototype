package com.carrerlens.demo.job.repository;


import com.carrerlens.demo.job.entity.JobPostingRaw;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface JobPostingRawRepository extends JpaRepository<JobPostingRaw, Long> {

    Optional<JobPostingRaw> findBySourceAndSourceJobId(String source, Long sourceJobId);
    List<JobPostingRaw> findAllByStatus(com.carrerlens.demo.job.eumtype.JobStatus status);

}