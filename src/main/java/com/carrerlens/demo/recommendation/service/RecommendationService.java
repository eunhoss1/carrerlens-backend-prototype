package com.carrerlens.demo.recommendation.service;

import com.carrerlens.demo.job.entity.JobPostingNormalized;
import com.carrerlens.demo.job.entity.JobPostingSkill;
import com.carrerlens.demo.job.repository.JobPostingNormalizedRepository;
import com.carrerlens.demo.job.repository.JobPostingSkillRepository;
import com.carrerlens.demo.recommendation.dto.RecommendationResponse;
import com.carrerlens.demo.user.entity.UserProfile;
import com.carrerlens.demo.user.entity.UserSkill;
import com.carrerlens.demo.user.repository.UserProfileRepository;
import com.carrerlens.demo.user.repository.UserSkillRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class RecommendationService {

    private final UserProfileRepository userProfileRepository;
    private final UserSkillRepository userSkillRepository;
    private final JobPostingNormalizedRepository jobPostingNormalizedRepository;
    private final JobPostingSkillRepository jobPostingSkillRepository;
    private final LlmAnalysisService llmAnalysisService;

    public List<RecommendationResponse> recommend(Long userId) {
        UserProfile user = userProfileRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("사용자를 찾을 수 없습니다. id=" + userId));

        Set<String> userSkillCodes = userSkillRepository.findByUser(user).stream()
                .map(UserSkill::getSkillTag)
                .map(skillTag -> skillTag.getCode())
                .collect(Collectors.toSet());

        List<JobPostingNormalized> jobs = jobPostingNormalizedRepository.findByItJobTrue();

        List<RecommendationTemp> tempResults = new ArrayList<>();

        for (JobPostingNormalized job : jobs) {
            if (!passHardFilter(user, job)) {
                continue;
            }

            Set<String> jobSkillCodes = jobPostingSkillRepository.findByJob(job).stream()
                    .map(JobPostingSkill::getSkillTag)
                    .map(skillTag -> skillTag.getCode())
                    .collect(Collectors.toSet());

            List<String> matchedSkills = jobSkillCodes.stream()
                    .filter(userSkillCodes::contains)
                    .sorted()
                    .toList();

            List<String> missingSkills = jobSkillCodes.stream()
                    .filter(code -> !userSkillCodes.contains(code))
                    .sorted()
                    .limit(5)
                    .toList();

            int score = 0;
            score += calculateSkillScore(userSkillCodes, jobSkillCodes); // 최대 50
            score += calculateCountryScore(user, job);                   // 최대 20
            score += calculateJobScore(user, job);                       // 최대 20
            score += calculateLanguageScore(user, job);                  // 최대 10

            String reason = buildReason(user, job, matchedSkills, missingSkills);

            tempResults.add(new RecommendationTemp(
                    job.getId(),
                    job.getNormalizedTitle(),
                    job.getCountryCode(),
                    score,
                    matchedSkills,
                    missingSkills,
                    reason
            ));
        }

        List<RecommendationTemp> topResults = tempResults.stream()
                .sorted(Comparator.comparingInt(RecommendationTemp::score).reversed())
                .limit(5)
                .toList();

        List<RecommendationResponse> finalResults = new ArrayList<>();

        for (RecommendationTemp temp : topResults) {
            String llmAnalysis = llmAnalysisService.analyzeJob(
                    user.getDesiredJobCategory(),
                    user.getLanguageLevel(),
                    temp.title(),
                    temp.countryCode(),
                    temp.matchedSkills(),
                    temp.missingSkills()
            );

            finalResults.add(new RecommendationResponse(
                    temp.jobId(),
                    temp.title(),
                    temp.countryCode(),
                    temp.score(),
                    temp.matchedSkills(),
                    temp.missingSkills(),
                    temp.reason(),
                    llmAnalysis
            ));
        }

        return finalResults;
    }

    private boolean passHardFilter(UserProfile user, JobPostingNormalized job) {
        if (user.getPreferredCountryCode() != null && !user.getPreferredCountryCode().isBlank()) {
            if (job.getCountryCode() == null || !user.getPreferredCountryCode().equalsIgnoreCase(job.getCountryCode())) {
                return false;
            }
        }

        return true;
    }

    private int calculateSkillScore(Set<String> userSkillCodes, Set<String> jobSkillCodes) {
        if (jobSkillCodes == null || jobSkillCodes.isEmpty()) {
            return 0;
        }

        long matchedCount = jobSkillCodes.stream()
                .filter(userSkillCodes::contains)
                .count();

        double ratioScore = ((double) matchedCount / jobSkillCodes.size()) * 35.0;
        double countBonus = Math.min(matchedCount * 5.0, 15.0);

        return (int) Math.round(ratioScore + countBonus);
    }

    private int calculateCountryScore(UserProfile user, JobPostingNormalized job) {
        if (user.getPreferredCountryCode() != null
                && job.getCountryCode() != null
                && user.getPreferredCountryCode().equalsIgnoreCase(job.getCountryCode())) {
            return 20;
        }
        return 0;
    }

    private int calculateJobScore(UserProfile user, JobPostingNormalized job) {
        return matchesDesiredJob(user.getDesiredJobCategory(), job.getNormalizedTitle()) ? 20 : 0;
    }

    private int calculateLanguageScore(UserProfile user, JobPostingNormalized job) {
        if (user.getLanguageLevel() != null && !user.getLanguageLevel().isBlank()) {
            return 10;
        }
        return 0;
    }

    private boolean matchesDesiredJob(String desiredJobCategory, String normalizedTitle) {
        if (desiredJobCategory == null || normalizedTitle == null) {
            return false;
        }

        String desired = desiredJobCategory.toUpperCase();
        String title = normalizedTitle.toLowerCase();

        return switch (desired) {
            case "BACKEND" -> title.contains("backend") || title.contains("server") || title.contains("java");
            case "FRONTEND" -> title.contains("frontend") || title.contains("react") || title.contains("web");
            case "DATA" -> title.contains("data") || title.contains("ai") || title.contains("ml");
            default -> false;
        };
    }

    private String buildReason(UserProfile user,
                               JobPostingNormalized job,
                               List<String> matchedSkills,
                               List<String> missingSkills) {

        List<String> parts = new ArrayList<>();

        if (!matchedSkills.isEmpty()) {
            parts.add("보유 기술스택과 공고 요구 기술이 일부 일치합니다");
        }

        if (user.getPreferredCountryCode() != null
                && job.getCountryCode() != null
                && user.getPreferredCountryCode().equalsIgnoreCase(job.getCountryCode())) {
            parts.add("희망 국가와 공고 국가가 일치합니다");
        }

        if (matchesDesiredJob(user.getDesiredJobCategory(), job.getNormalizedTitle())) {
            parts.add("희망 직무 방향과 공고가 잘 맞습니다");
        }

        if (!missingSkills.isEmpty()) {
            parts.add("부족한 기술은 " + String.join(", ", missingSkills) + " 입니다");
        }

        if (parts.isEmpty()) {
            return "기본 조건을 충족한 공고입니다.";
        }

        return String.join(". ", parts) + ".";
    }

    private record RecommendationTemp(
            Long jobId,
            String title,
            String countryCode,
            int score,
            List<String> matchedSkills,
            List<String> missingSkills,
            String reason
    ) {
    }
}