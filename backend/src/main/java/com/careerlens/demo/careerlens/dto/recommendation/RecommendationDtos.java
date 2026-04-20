package com.careerlens.demo.careerlens.dto.recommendation;

import java.util.List;

public class RecommendationDtos {
    public record DiagnoseRequest(Long userId, Long targetJobId) {}
    public record JobCard(Long jobId, String title, String company, String country, String jobFamily) {}
    public record DiagnosisResponse(Long diagnosisId, JobCard targetJob, List<String> missingElements, String readinessLevel, String applyDecision) {}
}
