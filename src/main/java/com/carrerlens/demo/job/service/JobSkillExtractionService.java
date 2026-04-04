package com.carrerlens.demo.job.service;

import com.carrerlens.demo.job.entity.JobPostingNormalized;
import com.carrerlens.demo.job.entity.JobPostingRaw;
import com.carrerlens.demo.job.entity.JobPostingSkill;
import com.carrerlens.demo.job.entity.SkillTag;
import com.carrerlens.demo.job.repository.JobPostingNormalizedRepository;
import com.carrerlens.demo.job.repository.JobPostingSkillRepository;
import com.carrerlens.demo.job.repository.SkillTagRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.carrerlens.demo.job.dto.JobSkillResponse;
import com.carrerlens.demo.job.entity.JobPostingSkill;
import java.util.List;

import java.util.*;

@Service
@Transactional
public class JobSkillExtractionService {

    private final JobPostingNormalizedRepository jobPostingNormalizedRepository;
    private final JobPostingSkillRepository jobPostingSkillRepository;
    private final SkillTagRepository skillTagRepository;

    private static final Map<String, List<String>> SKILL_KEYWORD_MAP = Map.ofEntries(
            Map.entry("JAVA", List.of("java")),
            Map.entry("SPRING", List.of("spring framework", "spring")),
            Map.entry("SPRING_BOOT", List.of("spring boot", "springboot")),
            Map.entry("JPA", List.of("jpa", "java persistence api", "spring data jpa")),
            Map.entry("HIBERNATE", List.of("hibernate")),

            Map.entry("MYSQL", List.of("mysql")),
            Map.entry("POSTGRESQL", List.of("postgresql", "postgres")),
            Map.entry("REDIS", List.of("redis")),
            Map.entry("MONGODB", List.of("mongodb", "mongo db", "mongo-db")),

            Map.entry("AWS", List.of("aws", "amazon web services")),
            Map.entry("DOCKER", List.of("docker", "dockerized")),
            Map.entry("KUBERNETES", List.of("kubernetes", "k8s")),
            Map.entry("JENKINS", List.of("jenkins")),
            Map.entry("TERRAFORM", List.of("terraform")),

            Map.entry("JAVASCRIPT", List.of("javascript")),
            Map.entry("TYPESCRIPT", List.of("typescript")),
            Map.entry("REACT", List.of("react", "react.js", "reactjs")),
            Map.entry("NEXTJS", List.of("next.js", "nextjs", "next js")),
            Map.entry("VUE", List.of("vue", "vue.js", "vuejs")),

            Map.entry("PYTHON", List.of("python")),
            Map.entry("PANDAS", List.of("pandas")),
            Map.entry("PYTORCH", List.of("pytorch")),
            Map.entry("TENSORFLOW", List.of("tensorflow")),
            Map.entry("SPARK", List.of("apache spark", "spark")),
            Map.entry("AIRFLOW", List.of("apache airflow", "airflow")),
            Map.entry("FASTAPI", List.of("fastapi", "fast api")),
            Map.entry("FLASK", List.of("flask")),
            Map.entry("DJANGO", List.of("django")),
            Map.entry("LLM", List.of("llm", "large language model", "large language models", "generative ai"))
    );

    public JobSkillExtractionService(JobPostingNormalizedRepository jobPostingNormalizedRepository,
                                     SkillTagRepository skillTagRepository,
                                     JobPostingSkillRepository jobPostingSkillRepository) {
        this.jobPostingNormalizedRepository = jobPostingNormalizedRepository;
        this.skillTagRepository = skillTagRepository;
        this.jobPostingSkillRepository = jobPostingSkillRepository;
    }



    @Transactional
    public int extractSkillsFromItJobs() {
        List<JobPostingNormalized> jobs = jobPostingNormalizedRepository.findByItJobTrue();

        int savedCount = 0;

        for (JobPostingNormalized job : jobs) {
            // 기존 매핑 삭제
            jobPostingSkillRepository.deleteByJob(job);
            jobPostingSkillRepository.flush();

            JobPostingRaw raw = job.getRawJob();
            String content = safeLower(raw.getContentRaw());
            String title = safeLower(raw.getTitle());

            Set<String> matchedSkills = extractSkillCodes(title + " " + content);

            for (String skillCode : matchedSkills) {
                SkillTag skillTag = findOrCreateSkillTag(skillCode);

                JobPostingSkill jobPostingSkill = new JobPostingSkill();
                jobPostingSkill.setJob(job);
                jobPostingSkill.setSkillTag(skillTag);
                jobPostingSkill.setRequired(true);

                jobPostingSkillRepository.save(jobPostingSkill);
                savedCount++;
            }
        }

        return savedCount;
    }

    @Transactional(readOnly = true)
    public JobSkillResponse getJobSkills(Long jobId) {
        JobPostingNormalized job = jobPostingNormalizedRepository.findById(jobId)
                .orElseThrow(() -> new IllegalArgumentException("해당 공고가 없습니다. id=" + jobId));

        List<JobPostingSkill> jobSkills = jobPostingSkillRepository.findByJob(job);

        List<String> skills = jobSkills.stream()
                .map(js -> js.getSkillTag().getDisplayName())
                .sorted()
                .toList();

        return new JobSkillResponse(
                job.getId(),
                job.getNormalizedTitle(),
                job.getCountryCode(),
                skills
        );
    }

    private Set<String> extractSkillCodes(String text) {
        Set<String> result = new HashSet<>();

        if (text == null || text.isBlank()) {
            return result;
        }

        String lowerText = text.toLowerCase();

        for (Map.Entry<String, List<String>> entry : SKILL_KEYWORD_MAP.entrySet()) {
            String standardCode = entry.getKey();

            List<String> keywords = entry.getValue().stream()
                    .sorted(Comparator.comparingInt(String::length).reversed())
                    .toList();

            for (String keyword : keywords) {
                if (lowerText.contains(keyword.toLowerCase())) {
                    result.add(standardCode);
                    break;
                }
            }
        }

        return result;
    }

    private void addIfContains(Set<String> results, String source, String code, String... keywords) {
        for (String keyword : keywords) {
            if (source.contains(keyword)) {
                results.add(code);
                return;
            }
        }
    }

    private SkillTag findOrCreateSkillTag(String code) {
        return skillTagRepository.findByCode(code)
                .orElseGet(() -> {
                    SkillTag skillTag = new SkillTag();
                    skillTag.setCode(code);
                    skillTag.setDisplayName(toDisplayName(code));
                    skillTag.setCategory(toCategory(code));
                    return skillTagRepository.save(skillTag);
                });
    }

    private String toDisplayName(String code) {
        return switch (code) {
            case "REACT" -> "React";
            case "TYPESCRIPT" -> "TypeScript";
            case "JAVASCRIPT" -> "JavaScript";
            case "NEXTJS" -> "Next.js";
            case "VUE" -> "Vue";
            case "ANGULAR" -> "Angular";
            case "JAVA" -> "Java";
            case "SPRING" -> "Spring";
            case "JPA" -> "JPA";
            case "KOTLIN" -> "Kotlin";
            case "NODEJS" -> "Node.js";
            case "EXPRESS" -> "Express";
            case "PYTHON" -> "Python";
            case "DJANGO" -> "Django";
            case "FASTAPI" -> "FastAPI";
            case "FLASK" -> "Flask";
            case "MYSQL" -> "MySQL";
            case "POSTGRESQL" -> "PostgreSQL";
            case "MONGODB" -> "MongoDB";
            case "REDIS" -> "Redis";
            case "AWS" -> "AWS";
            case "GCP" -> "GCP";
            case "AZURE" -> "Azure";
            case "DOCKER" -> "Docker";
            case "KUBERNETES" -> "Kubernetes";
            case "TERRAFORM" -> "Terraform";
            case "GIT" -> "Git";
            case "GITHUB" -> "GitHub";
            case "CI_CD" -> "CI/CD";
            case "JENKINS" -> "Jenkins";
            case "LLM" -> "LLM";
            case "PYTORCH" -> "PyTorch";
            case "TENSORFLOW" -> "TensorFlow";
            case "SPARK" -> "Spark";
            case "AIRFLOW" -> "Airflow";
            default -> code;
        };
    }

    private String toCategory(String code) {
        return switch (code) {
            case "JAVA", "SPRING", "JPA", "KOTLIN", "NODEJS", "EXPRESS" -> "BACKEND";
            case "REACT", "TYPESCRIPT", "JAVASCRIPT", "NEXTJS", "VUE", "ANGULAR" -> "FRONTEND";
            case "PYTHON", "DJANGO", "FASTAPI", "FLASK", "PYTORCH", "TENSORFLOW", "SPARK", "AIRFLOW", "LLM" -> "DATA";
            case "AWS", "GCP", "AZURE", "DOCKER", "KUBERNETES", "TERRAFORM", "JENKINS", "CI_CD" -> "DEVOPS";
            case "MYSQL", "POSTGRESQL", "MONGODB", "REDIS" -> "DATABASE";
            default -> "COMMON";
        };
    }

    private String safeLower(String value) {
        return value == null ? "" : value.toLowerCase(Locale.ROOT);
    }
}