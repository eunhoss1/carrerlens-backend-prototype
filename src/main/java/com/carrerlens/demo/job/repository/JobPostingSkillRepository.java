package com.carrerlens.demo.job.repository;


import com.carrerlens.demo.job.entity.JobPostingNormalized;
import com.carrerlens.demo.job.entity.JobPostingSkill;
import com.carrerlens.demo.job.entity.SkillTag;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface JobPostingSkillRepository extends JpaRepository<JobPostingSkill, Long> {

    Optional<JobPostingSkill> findByJobAndSkillTag(JobPostingNormalized job, SkillTag skillTag);

    List<JobPostingSkill> findByJob(JobPostingNormalized job);

    void deleteByJob(JobPostingNormalized job);
}