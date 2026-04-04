package com.carrerlens.demo.job.repository;



import com.carrerlens.demo.job.entity.JobPostingNormalized;
import com.carrerlens.demo.job.entity.JobPostingRaw;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface JobPostingNormalizedRepository extends JpaRepository<JobPostingNormalized, Long> {

    Optional<JobPostingNormalized> findByRawJob(JobPostingRaw rawJob);
    List<JobPostingNormalized> findByItJobTrue();
}