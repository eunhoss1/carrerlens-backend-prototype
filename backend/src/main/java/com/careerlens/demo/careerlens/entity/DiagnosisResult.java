package com.careerlens.demo.careerlens.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "diagnosis_result")
@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class DiagnosisResult extends BaseTimeEntity {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne(fetch = FetchType.LAZY) @JoinColumn(name = "user_id", nullable = false)
    private User user;
    @ManyToOne(fetch = FetchType.LAZY) @JoinColumn(name = "target_job_id", nullable = false)
    private JobPosting targetJob;
    @Column(name = "missing_elements", length = 1000)
    private String missingElements;
    @Enumerated(EnumType.STRING) @Column(name = "readiness_level", nullable = false)
    private Enums.ReadinessLevel readinessLevel;
    @Enumerated(EnumType.STRING) @Column(name = "apply_decision", nullable = false)
    private Enums.ApplyDecision applyDecision;
}
