package com.carrerlens.demo.job.repository;



import com.carrerlens.demo.job.entity.SkillTag;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface SkillTagRepository extends JpaRepository<SkillTag, Long> {
    Optional<SkillTag> findByCode(String code);
}
