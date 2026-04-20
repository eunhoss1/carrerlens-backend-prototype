package com.careerlens.demo.careerlens.controller;

import com.careerlens.demo.careerlens.common.ApiResponse;
import com.careerlens.demo.careerlens.dto.application.ApplicationDtos;
import com.careerlens.demo.careerlens.service.ApplicationService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/applications")
@RequiredArgsConstructor
public class ApplicationController {
    private final ApplicationService applicationService;

    @PostMapping
    public ApiResponse<ApplicationDtos.ApplicationResponse> create(@RequestBody ApplicationDtos.CreateApplicationRequest request) {
        return ApiResponse.ok("Application created", applicationService.create(request));
    }

    @GetMapping("/{userId}")
    public ApiResponse<List<ApplicationDtos.ApplicationResponse>> get(@PathVariable Long userId) {
        return ApiResponse.ok("Applications fetched", applicationService.getByUser(userId));
    }

    @PatchMapping("/{applicationId}/status")
    public ApiResponse<ApplicationDtos.ApplicationResponse> update(@PathVariable Long applicationId, @RequestBody ApplicationDtos.UpdateApplicationStatusRequest request) {
        return ApiResponse.ok("Application status updated", applicationService.updateStatus(applicationId, request));
    }
}
