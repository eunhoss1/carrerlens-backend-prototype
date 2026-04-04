package com.carrerlens.demo.job.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "skill_tag")
@Getter
@Setter
@NoArgsConstructor
public class SkillTag {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true, length = 100)
    private String code;   // REACT, TYPESCRIPT, SPRING

    @Column(nullable = false, length = 100)
    private String displayName; // React, TypeScript, Spring

    @Column(length = 50)
    private String category; // BACKEND, FRONTEND, DATA, DEVOPS
}