package com.carrerlens.demo.user.entity;

import com.carrerlens.demo.job.entity.SkillTag;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(
        name = "user_skill",
        uniqueConstraints = {
                @UniqueConstraint(columnNames = {"user_id", "skill_tag_id"})
        }
)
@Getter
@Setter
@NoArgsConstructor
public class UserSkill {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private UserProfile user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "skill_tag_id", nullable = false)
    private SkillTag skillTag;
}