package com.careerlens.demo.careerlens.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "pattern_profile")
@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class PatternProfile extends BaseTimeEntity {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "job_family", nullable = false)
    private String jobFamily;
    @Column(name = "pattern_name", nullable = false)
    private String patternName;
    @Column(name = "core_skills", length = 1000)
    private String coreSkills;
}
