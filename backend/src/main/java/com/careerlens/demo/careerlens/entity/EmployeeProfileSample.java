package com.careerlens.demo.careerlens.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "employee_profile_sample")
@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class EmployeeProfileSample extends BaseTimeEntity {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "job_family", nullable = false)
    private String jobFamily;
    @Column(name = "country", nullable = false)
    private String country;
    @Column(name = "skills", length = 1000)
    private String skills;
    @Column(name = "experience_summary", length = 1000)
    private String experienceSummary;
}
