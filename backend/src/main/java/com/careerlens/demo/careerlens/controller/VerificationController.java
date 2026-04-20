package com.careerlens.demo.careerlens.controller;

import com.careerlens.demo.careerlens.common.ApiResponse;
import com.careerlens.demo.careerlens.dto.verification.VerificationDtos;
import com.careerlens.demo.careerlens.service.VerificationService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class VerificationController {
    private final VerificationService verificationService;

    @PostMapping("/api/verifications")
    public ApiResponse<VerificationDtos.VerificationResponse> create(@RequestBody VerificationDtos.CreateVerificationRequest request) {
        return ApiResponse.ok("Verification requested", verificationService.create(request));
    }

    @GetMapping("/api/verifications/{userId}")
    public ApiResponse<List<VerificationDtos.VerificationResponse>> byUser(@PathVariable Long userId) {
        return ApiResponse.ok("Verification requests fetched", verificationService.byUser(userId));
    }

    @GetMapping("/api/recruiter/verified-candidates")
    public ApiResponse<List<VerificationDtos.VerificationResponse>> recruiterList() {
        return ApiResponse.ok("Verified candidates fetched", verificationService.recruiterList());
    }

    @PatchMapping("/api/admin/verifications/{verificationId}")
    public ApiResponse<VerificationDtos.VerificationResponse> adminUpdate(@PathVariable Long verificationId, @RequestBody VerificationDtos.UpdateVerificationStatusRequest request) {
        return ApiResponse.ok("Verification updated", verificationService.adminUpdate(verificationId, request));
    }
}
