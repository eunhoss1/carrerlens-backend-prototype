package com.carrerlens.demo.job.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "job_posting_normalized")
@Getter
@Setter
@NoArgsConstructor
public class JobPostingNormalized {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "raw_job_id", nullable = false, unique = true)
    private JobPostingRaw rawJob;

    @Column(name = "country_code", length = 20)
    private String countryCode;

    @Column(length = 100)
    private String city;

    @Column(name = "job_category", length = 100)
    private String jobCategory;

    @Column(name = "normalized_title", length = 300)
    private String normalizedTitle;

    @Column(name = "is_it_job", nullable = false)
    private boolean itJob;
}