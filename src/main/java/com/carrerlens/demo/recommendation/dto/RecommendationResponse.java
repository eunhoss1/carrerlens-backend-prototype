package com.carrerlens.demo.recommendation.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;

@Getter
@AllArgsConstructor
public class RecommendationResponse {
    private Long jobId;
    private String title;
    private String countryCode;
    private int score;
    private List<String> matchedSkills;
    private List<String> missingSkills;
    private String reason;
    private String llmAnalysis;
}