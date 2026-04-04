package com.carrerlens.demo.user.service;

import com.carrerlens.demo.job.entity.SkillTag;
import com.carrerlens.demo.job.repository.SkillTagRepository;
import com.carrerlens.demo.user.dto.CreateUserProfileRequest;
import com.carrerlens.demo.user.dto.UserSkillRequest;
import com.carrerlens.demo.user.entity.UserProfile;
import com.carrerlens.demo.user.entity.UserSkill;
import com.carrerlens.demo.user.repository.UserProfileRepository;
import com.carrerlens.demo.user.repository.UserSkillRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class UserService {

    private final UserProfileRepository userProfileRepository;
    private final UserSkillRepository userSkillRepository;
    private final SkillTagRepository skillTagRepository;

    public UserProfile createUser(CreateUserProfileRequest request) {
        UserProfile user = new UserProfile();
        user.setName(request.getName());
        user.setDesiredJobCategory(request.getDesiredJobCategory());
        user.setPreferredCountryCode(request.getPreferredCountryCode());
        user.setLanguageLevel(request.getLanguageLevel());

        return userProfileRepository.save(user);
    }

    public String saveUserSkills(Long userId, UserSkillRequest request) {
        UserProfile user = userProfileRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("사용자를 찾을 수 없습니다. id=" + userId));

        // 기존 스킬 전부 삭제 후 다시 저장
        userSkillRepository.deleteByUser(user);
        userSkillRepository.flush();

        int savedCount = 0;

        for (String skillCode : request.getSkillCodes()) {
            SkillTag skillTag = skillTagRepository.findByCode(skillCode)
                    .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 스킬 코드입니다. code=" + skillCode));

            UserSkill userSkill = new UserSkill();
            userSkill.setUser(user);
            userSkill.setSkillTag(skillTag);

            userSkillRepository.save(userSkill);
            savedCount++;
        }

        return savedCount + "개의 사용자 스킬을 저장했습니다.";
    }
}