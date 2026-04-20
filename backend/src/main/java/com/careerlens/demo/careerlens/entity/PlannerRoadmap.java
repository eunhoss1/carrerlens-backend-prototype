package com.careerlens.demo.careerlens.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "planner_roadmap")
@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class PlannerRoadmap extends BaseTimeEntity {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne(fetch = FetchType.LAZY) @JoinColumn(name = "user_id", nullable = false)
    private User user;
    @ManyToOne(fetch = FetchType.LAZY) @JoinColumn(name = "diagnosis_result_id", nullable = false)
    private DiagnosisResult diagnosisResult;
    @Column(name = "roadmap_title", nullable = false)
    private String roadmapTitle;
}
