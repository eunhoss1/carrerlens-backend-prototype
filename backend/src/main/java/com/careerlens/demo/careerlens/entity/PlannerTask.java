package com.careerlens.demo.careerlens.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "planner_task")
@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class PlannerTask extends BaseTimeEntity {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne(fetch = FetchType.LAZY) @JoinColumn(name = "roadmap_id", nullable = false)
    private PlannerRoadmap roadmap;
    @Column(name = "phase", nullable = false)
    private Integer phase;
    @Column(name = "title", nullable = false)
    private String title;
    @Column(name = "is_completed", nullable = false)
    private boolean completed;
}
