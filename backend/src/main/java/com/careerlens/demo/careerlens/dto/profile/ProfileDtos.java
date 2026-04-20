package com.careerlens.demo.careerlens.dto.profile;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public class ProfileDtos {
    public record CreateProfileRequest(@NotNull Long userId, @NotBlank String targetCountry, @NotBlank String targetJobFamily, String skills, String experienceSummary) {}
    public record ProfileResponse(Long userId, String name, String targetCountry, String targetJobFamily, String skills, String experienceSummary) {}
}
