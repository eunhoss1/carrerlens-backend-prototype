package com.careerlens.demo.careerlens.service;

import com.careerlens.demo.careerlens.dto.recommendation.RecommendationDtos;
import com.careerlens.demo.careerlens.entity.*;
import com.careerlens.demo.careerlens.repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;

@Service
@RequiredArgsConstructor
public class RecommendationServiceV2 {
    private final UserRepository userRepository;
    private final JobPostingRepository jobPostingRepository;
    private final EmployeeProfileSampleRepository employeeProfileSampleRepository;
    private final PatternProfileRepository patternProfileRepository;
    private final DiagnosisResultRepository diagnosisResultRepository;

    public RecommendationDtos.DiagnosisResponse diagnose(RecommendationDtos.DiagnoseRequest request) {
        User user = userRepository.findById(request.userId()).orElseThrow();
        JobPosting job = jobPostingRepository.findById(request.targetJobId()).orElseThrow();

        List<String> required = Arrays.stream(job.getRequiredSkills().split(",")).map(String::trim).toList();

        String sampleSkills = employeeProfileSampleRepository.findAll().stream()
                .filter(s -> s.getJobFamily().equalsIgnoreCase(job.getJobFamily()))
                .findFirst().map(EmployeeProfileSample::getSkills).orElse("");

        String patternSkills = patternProfileRepository.findAll().stream()
                .filter(p -> p.getJobFamily().equalsIgnoreCase(job.getJobFamily()))
                .findFirst().map(PatternProfile::getCoreSkills).orElse("");

        List<String> baselineSkills = Arrays.stream((sampleSkills + "," + patternSkills).split(","))
                .map(String::trim)
                .filter(skill -> !skill.isBlank())
                .distinct()
                .toList();

        List<String> missing = required.stream().filter(skill -> !baselineSkills.contains(skill)).toList();

        Enums.ReadinessLevel level = missing.size() <= 1 ? Enums.ReadinessLevel.HIGH : missing.size() <= 3 ? Enums.ReadinessLevel.MEDIUM : Enums.ReadinessLevel.LOW;
        Enums.ApplyDecision decision = switch (level) {
            case HIGH -> Enums.ApplyDecision.APPLY_NOW;
            case MEDIUM -> Enums.ApplyDecision.APPLY_AFTER_PREP;
            case LOW -> Enums.ApplyDecision.LONG_TERM_PREP;
        };

        DiagnosisResult saved = diagnosisResultRepository.save(DiagnosisResult.builder()
                .user(user)
                .targetJob(job)
                .missingElements(String.join(", ", missing))
                .readinessLevel(level)
                .applyDecision(decision)
                .build());

        return toResponse(saved);
    }

    public List<RecommendationDtos.DiagnosisResponse> getUserDiagnoses(Long userId) {
        return diagnosisResultRepository.findByUserId(userId).stream().map(this::toResponse).toList();
    }

    private RecommendationDtos.DiagnosisResponse toResponse(DiagnosisResult d) {
        return new RecommendationDtos.DiagnosisResponse(
                d.getId(),
                new RecommendationDtos.JobCard(d.getTargetJob().getId(), d.getTargetJob().getTitle(), d.getTargetJob().getCompany(), d.getTargetJob().getCountry(), d.getTargetJob().getJobFamily()),
                d.getMissingElements() == null || d.getMissingElements().isBlank() ? List.of() : Arrays.stream(d.getMissingElements().split(",")).map(String::trim).toList(),
                d.getReadinessLevel().name(),
                d.getApplyDecision().name());
    }
}
