package com.careerlens.demo.careerlens.controller;

import com.careerlens.demo.careerlens.common.ApiResponse;
import com.careerlens.demo.careerlens.dto.recommendation.RecommendationDtos;
import com.careerlens.demo.careerlens.service.RecommendationServiceV2;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/recommendations")
@RequiredArgsConstructor
public class RecommendationControllerV2 {
    private final RecommendationServiceV2 recommendationService;

    @PostMapping("/diagnose")
    public ApiResponse<RecommendationDtos.DiagnosisResponse> diagnose(@RequestBody RecommendationDtos.DiagnoseRequest request) {
        return ApiResponse.ok("Diagnosis completed", recommendationService.diagnose(request));
    }

    @GetMapping("/{userId}")
    public ApiResponse<List<RecommendationDtos.DiagnosisResponse>> get(@PathVariable Long userId) {
        return ApiResponse.ok("Diagnosis history fetched", recommendationService.getUserDiagnoses(userId));
    }
}
