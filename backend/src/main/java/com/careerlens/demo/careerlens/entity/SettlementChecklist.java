package com.careerlens.demo.careerlens.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "settlement_checklist")
@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class SettlementChecklist extends BaseTimeEntity {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "country", nullable = false)
    private String country;
    @Column(name = "timeline_stage", nullable = false)
    private String timelineStage;
    @Column(name = "task_title", nullable = false)
    private String taskTitle;
    @Column(name = "reference_link")
    private String referenceLink;
}
