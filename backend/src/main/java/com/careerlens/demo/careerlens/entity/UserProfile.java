package com.careerlens.demo.careerlens.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "user_profile")
@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class UserProfile extends BaseTimeEntity {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false, unique = true)
    private User user;

    @Column(name = "target_country")
    private String targetCountry;

    @Column(name = "target_job_family")
    private String targetJobFamily;

    @Column(name = "skills", length = 1000)
    private String skills;

    @Column(name = "experience_summary", length = 1000)
    private String experienceSummary;
}
