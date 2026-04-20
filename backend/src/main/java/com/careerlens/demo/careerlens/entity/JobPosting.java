package com.careerlens.demo.careerlens.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "job_posting")
@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class JobPosting extends BaseTimeEntity {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "title", nullable = false)
    private String title;
    @Column(name = "company", nullable = false)
    private String company;
    @Column(name = "country", nullable = false)
    private String country;
    @Column(name = "job_family", nullable = false)
    private String jobFamily;
    @Column(name = "required_skills", length = 1000)
    private String requiredSkills;
}
