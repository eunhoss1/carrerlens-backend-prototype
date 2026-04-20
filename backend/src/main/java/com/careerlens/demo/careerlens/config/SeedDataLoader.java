package com.careerlens.demo.careerlens.config;

import com.careerlens.demo.careerlens.entity.*;
import com.careerlens.demo.careerlens.repository.*;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.Map;

@Component
@RequiredArgsConstructor
public class SeedDataLoader implements CommandLineRunner {
    private final ObjectMapper objectMapper;
    private final UserRepository userRepository;
    private final JobPostingRepository jobPostingRepository;
    private final EmployeeProfileSampleRepository employeeProfileSampleRepository;
    private final PatternProfileRepository patternProfileRepository;
    private final SettlementChecklistRepository settlementChecklistRepository;
    private final VerificationRequestRepository verificationRequestRepository;

    @Override
    public void run(String... args) throws Exception {
        Path seedDir = Path.of("seed-data");
        if (!Files.exists(seedDir)) {
            seedDir = Path.of("..", "seed-data");
        }
        if (!Files.exists(seedDir)) return;

        if (userRepository.count() == 0) {
            List<Map<String, String>> users = objectMapper.readValue(seedDir.resolve("seed_users.json").toFile(), new TypeReference<>() {});
            users.forEach(u -> userRepository.save(User.builder().name(u.get("name")).email(u.get("email")).role(Enums.UserRole.valueOf(u.get("role"))).build()));
        }
        if (jobPostingRepository.count() == 0) {
            List<Map<String, String>> jobs = objectMapper.readValue(seedDir.resolve("seed_jobs.json").toFile(), new TypeReference<>() {});
            jobs.forEach(j -> jobPostingRepository.save(JobPosting.builder().title(j.get("title")).company(j.get("company")).country(j.get("country")).jobFamily(j.get("job_family")).requiredSkills(j.get("required_skills")).build()));
        }
        if (employeeProfileSampleRepository.count() == 0) {
            List<Map<String, String>> rows = objectMapper.readValue(seedDir.resolve("seed_employee_profiles.json").toFile(), new TypeReference<>() {});
            rows.forEach(r -> employeeProfileSampleRepository.save(EmployeeProfileSample.builder().jobFamily(r.get("job_family")).country(r.get("country")).skills(r.get("skills")).experienceSummary(r.get("experience_summary")).build()));
        }
        if (patternProfileRepository.count() == 0) {
            List<Map<String, String>> rows = objectMapper.readValue(seedDir.resolve("seed_pattern_profiles.json").toFile(), new TypeReference<>() {});
            rows.forEach(r -> patternProfileRepository.save(PatternProfile.builder().jobFamily(r.get("job_family")).patternName(r.get("pattern_name")).coreSkills(r.get("core_skills")).build()));
        }
        if (settlementChecklistRepository.count() == 0) {
            List<Map<String, String>> rows = objectMapper.readValue(seedDir.resolve("seed_country_checklists.json").toFile(), new TypeReference<>() {});
            rows.forEach(r -> settlementChecklistRepository.save(SettlementChecklist.builder().country(r.get("country")).timelineStage(r.get("timeline_stage")).taskTitle(r.get("task_title")).referenceLink(r.get("reference_link")).build()));
        }
        if (verificationRequestRepository.count() == 0 && userRepository.count() > 0) {
            List<Map<String, String>> rows = objectMapper.readValue(seedDir.resolve("seed_verification_requests.json").toFile(), new TypeReference<>() {});
            rows.forEach(r -> userRepository.findById(Long.valueOf(r.get("user_id"))).ifPresent(user -> verificationRequestRepository.save(
                    VerificationRequest.builder().user(user).evidenceUrl(r.get("evidence_url")).status(Enums.VerificationStatus.valueOf(r.get("status"))).adminComment(r.get("admin_comment")).build())));
        }
    }
}
