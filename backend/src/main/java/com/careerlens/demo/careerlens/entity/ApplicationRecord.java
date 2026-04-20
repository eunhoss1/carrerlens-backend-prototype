package com.careerlens.demo.careerlens.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "application_record")
@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class ApplicationRecord extends BaseTimeEntity {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne(fetch = FetchType.LAZY) @JoinColumn(name = "user_id", nullable = false)
    private User user;
    @ManyToOne(fetch = FetchType.LAZY) @JoinColumn(name = "job_posting_id", nullable = false)
    private JobPosting jobPosting;
    @Column(name = "resume_keywords", length = 1000)
    private String resumeKeywords;
    @Column(name = "portfolio_status")
    private String portfolioStatus;
    @Enumerated(EnumType.STRING) @Column(name = "status", nullable = false)
    private Enums.ApplicationStatus status;
}
