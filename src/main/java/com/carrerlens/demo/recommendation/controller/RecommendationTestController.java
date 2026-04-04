package com.carrerlens.demo.recommendation.controller;

import com.carrerlens.demo.recommendation.dto.RecommendationResponse;
import com.carrerlens.demo.recommendation.service.RecommendationService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/test/recommendations")
@RequiredArgsConstructor
public class RecommendationTestController {

    private final RecommendationService recommendationService;

    @GetMapping("/{userId}")
    public List<RecommendationResponse> recommend(@PathVariable Long userId) {
        return recommendationService.recommend(userId);
    }
}