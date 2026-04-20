package com.careerlens.demo.careerlens.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "verification_request")
@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class VerificationRequest extends BaseTimeEntity {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne(fetch = FetchType.LAZY) @JoinColumn(name = "user_id", nullable = false)
    private User user;
    @Column(name = "evidence_url", nullable = false)
    private String evidenceUrl;
    @Enumerated(EnumType.STRING) @Column(name = "status", nullable = false)
    private Enums.VerificationStatus status;
    @Column(name = "admin_comment")
    private String adminComment;
}
