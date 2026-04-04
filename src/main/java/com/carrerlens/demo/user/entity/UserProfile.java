package com.carrerlens.demo.user.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "user_profile")
@Getter
@Setter
@NoArgsConstructor
public class UserProfile {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    // BACKEND, FRONTEND, DATA
    private String desiredJobCategory;

    // US, JP, SG
    private String preferredCountryCode;

    private String languageLevel;
}