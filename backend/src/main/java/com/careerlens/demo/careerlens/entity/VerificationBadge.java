package com.careerlens.demo.careerlens.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "verification_badge")
@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class VerificationBadge extends BaseTimeEntity {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @OneToOne(fetch = FetchType.LAZY) @JoinColumn(name = "user_id", nullable = false, unique = true)
    private User user;
    @Column(name = "badge_name", nullable = false)
    private String badgeName;
    @Column(name = "is_active", nullable = false)
    private boolean active;
}
