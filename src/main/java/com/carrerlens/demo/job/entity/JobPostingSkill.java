package com.carrerlens.demo.job.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(
        name = "job_posting_skill",
        uniqueConstraints = {
                @UniqueConstraint(columnNames = {"job_id", "skill_tag_id"})
        }
)
@Getter
@Setter
@NoArgsConstructor
public class JobPostingSkill {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "job_id", nullable = false)
    private JobPostingNormalized job;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "skill_tag_id", nullable = false)
    private SkillTag skillTag;

    @Column(nullable = false)
    private boolean required;
}