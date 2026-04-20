package com.careerlens.demo.careerlens.controller;

import com.careerlens.demo.careerlens.common.ApiResponse;
import com.careerlens.demo.careerlens.dto.profile.ProfileDtos;
import com.careerlens.demo.careerlens.service.ProfileService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/profile")
@RequiredArgsConstructor
public class ProfileController {
    private final ProfileService profileService;

    @PostMapping
    public ApiResponse<ProfileDtos.ProfileResponse> create(@Valid @RequestBody ProfileDtos.CreateProfileRequest request) {
        return ApiResponse.ok("Profile saved", profileService.saveProfile(request));
    }

    @GetMapping("/{userId}")
    public ApiResponse<ProfileDtos.ProfileResponse> get(@PathVariable Long userId) {
        return ApiResponse.ok("Profile fetched", profileService.getProfile(userId));
    }
}
