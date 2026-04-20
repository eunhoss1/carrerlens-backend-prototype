package com.careerlens.demo.careerlens.controller;

import com.careerlens.demo.careerlens.common.ApiResponse;
import com.careerlens.demo.careerlens.dto.settlement.SettlementDtos;
import com.careerlens.demo.careerlens.service.SettlementService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/settlement")
@RequiredArgsConstructor
public class SettlementController {
    private final SettlementService settlementService;

    @GetMapping("/{country}")
    public ApiResponse<List<SettlementDtos.ChecklistResponse>> byCountry(@PathVariable String country) {
        return ApiResponse.ok("Settlement checklist fetched", settlementService.byCountry(country));
    }

    @GetMapping("/{userId}/checklist")
    public ApiResponse<List<SettlementDtos.ChecklistResponse>> byUser(@PathVariable Long userId) {
        return ApiResponse.ok("User settlement checklist fetched", settlementService.byUser(userId));
    }
}
