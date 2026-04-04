package com.carrerlens.demo.job.entity;

import com.carrerlens.demo.job.eumtype.JobStatus;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Table(
        name = "job_posting_raw",
        uniqueConstraints = {
                @UniqueConstraint(columnNames = {"source", "source_job_id"})
        }
)
@Getter
@Setter
@NoArgsConstructor
public class JobPostingRaw {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 50)
    private String source;

    @Column(name = "source_job_id", nullable = false)
    private Long sourceJobId;

    @Column(nullable = false, length = 300)
    private String title;

    @Column(name = "company_name", length = 200)
    private String companyName;

    @Column(name = "location_name", length = 300)
    private String locationName;

    @Column(name = "job_url", length = 1000)
    private String jobUrl;

    @Lob
    @Column(name = "content_raw", columnDefinition = "LONGTEXT")
    private String contentRaw;

    @Column(name = "language_code", length = 20)
    private String languageCode;

    @Column(name = "updated_at_raw", length = 100)
    private String updatedAtRaw;

    @Column(name = "first_published_raw", length = 100)
    private String firstPublishedRaw;

    @Lob
    @Column(name = "departments_raw", columnDefinition = "TEXT")
    private String departmentsRaw;

    @Lob
    @Column(name = "offices_raw", columnDefinition = "TEXT")
    private String officesRaw;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 20)
    private JobStatus status;

    @Column(name = "last_seen_at")
    private LocalDateTime lastSeenAt;

    @Column(name = "collected_at")
    private LocalDateTime collectedAt;
}