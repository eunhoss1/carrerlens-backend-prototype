package com.carrerlens.demo.job.dto;

import java.util.List;

public record JobSkillResponse(
        Long jobId,
        String normalizedTitle,
        String countryCode,
        List<String> skills
) {
}