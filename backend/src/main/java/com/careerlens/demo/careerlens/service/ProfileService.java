package com.careerlens.demo.careerlens.service;

import com.careerlens.demo.careerlens.dto.profile.ProfileDtos;
import com.careerlens.demo.careerlens.entity.User;
import com.careerlens.demo.careerlens.entity.UserProfile;
import com.careerlens.demo.careerlens.repository.UserProfileRepository;
import com.careerlens.demo.careerlens.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ProfileService {
    private final UserRepository userRepository;
    private final UserProfileRepository userProfileRepository;

    public ProfileDtos.ProfileResponse saveProfile(ProfileDtos.CreateProfileRequest request) {
        User user = userRepository.findById(request.userId()).orElseThrow();
        UserProfile profile = userProfileRepository.findByUserId(user.getId()).orElse(UserProfile.builder().user(user).build());
        profile.setTargetCountry(request.targetCountry());
        profile.setTargetJobFamily(request.targetJobFamily());
        profile.setSkills(request.skills());
        profile.setExperienceSummary(request.experienceSummary());
        UserProfile saved = userProfileRepository.save(profile);
        return new ProfileDtos.ProfileResponse(user.getId(), user.getName(), saved.getTargetCountry(), saved.getTargetJobFamily(), saved.getSkills(), saved.getExperienceSummary());
    }

    public ProfileDtos.ProfileResponse getProfile(Long userId) {
        UserProfile profile = userProfileRepository.findByUserId(userId).orElseThrow();
        return new ProfileDtos.ProfileResponse(profile.getUser().getId(), profile.getUser().getName(), profile.getTargetCountry(), profile.getTargetJobFamily(), profile.getSkills(), profile.getExperienceSummary());
    }
}
