package com.carrerlens.demo.user.repository;

import com.carrerlens.demo.job.entity.SkillTag;
import com.carrerlens.demo.user.entity.UserProfile;
import com.carrerlens.demo.user.entity.UserSkill;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface UserSkillRepository extends JpaRepository<UserSkill, Long> {

    List<UserSkill> findByUser(UserProfile user);

    void deleteByUser(UserProfile user);

    Optional<UserSkill> findByUserAndSkillTag(UserProfile user, SkillTag skillTag);
}