package com.carrerlens.demo.job.service;



import com.carrerlens.demo.job.entity.JobPostingNormalized;
import com.carrerlens.demo.job.entity.JobPostingRaw;
import com.carrerlens.demo.job.eumtype.JobStatus;
import com.carrerlens.demo.job.repository.JobPostingNormalizedRepository;
import com.carrerlens.demo.job.repository.JobPostingRawRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Locale;

@Service
public class JobNormalizationService {

    private final JobPostingRawRepository jobPostingRawRepository;
    private final JobPostingNormalizedRepository jobPostingNormalizedRepository;

    public JobNormalizationService(JobPostingRawRepository jobPostingRawRepository,
                                   JobPostingNormalizedRepository jobPostingNormalizedRepository) {
        this.jobPostingRawRepository = jobPostingRawRepository;
        this.jobPostingNormalizedRepository = jobPostingNormalizedRepository;
    }

    @Transactional
    public int normalizeOpenJobs() {
        List<JobPostingRaw> rawJobs = jobPostingRawRepository.findAllByStatus(JobStatus.OPEN);

        int count = 0;

        for (JobPostingRaw rawJob : rawJobs) {
            JobPostingNormalized normalized = jobPostingNormalizedRepository
                    .findByRawJob(rawJob)
                    .orElseGet(JobPostingNormalized::new);

            normalized.setRawJob(rawJob);

            String title = safeLower(rawJob.getTitle());
            String location = safeLower(rawJob.getLocationName());

            boolean itJob = isItJob(title);
            normalized.setItJob(itJob);

            normalized.setJobCategory(extractJobCategory(title));
            normalized.setNormalizedTitle(normalizeTitle(rawJob.getTitle()));
            normalized.setCountryCode(extractCountryCode(location));
            normalized.setCity(extractCity(rawJob.getLocationName()));

            jobPostingNormalizedRepository.save(normalized);
            count++;
        }

        return count;
    }

    private boolean isItJob(String title) {
        boolean included =
                containsAny(title,
                        "frontend", "front-end",
                        "backend", "back-end",
                        "full stack", "fullstack",
                        "software engineer", "software developer",
                        "developer", "engineer",
                        "data engineer", "data scientist", "data analyst",
                        "machine learning", "ml engineer",
                        "devops", "site reliability", "sre",
                        "qa engineer", "test engineer",
                        "security engineer",
                        "android", "ios", "mobile",
                        "platform engineer",
                        "ui engineer", "web engineer");

        boolean excluded =
                containsAny(title,
                        "marketing", "sales", "account executive",
                        "recruiter", "talent", "hr", "human resources",
                        "finance", "legal", "operations", "support",
                        "customer success", "business development",
                        "partnership", "content designer", "copywriter");

        return included && !excluded;
    }

    private String extractJobCategory(String title) {
        if (containsAny(title, "frontend", "front-end", "ui engineer", "web engineer")) return "FRONTEND";
        if (containsAny(title, "backend", "back-end")) return "BACKEND";
        if (containsAny(title, "full stack", "fullstack")) return "FULLSTACK";
        if (containsAny(title, "data engineer", "data scientist", "data analyst", "machine learning", "ml engineer")) return "DATA";
        if (containsAny(title, "devops", "site reliability", "sre", "platform engineer")) return "INFRA";
        if (containsAny(title, "android", "ios", "mobile")) return "MOBILE";
        if (containsAny(title, "qa engineer", "test engineer")) return "QA";
        if (containsAny(title, "security engineer")) return "SECURITY";
        if (containsAny(title, "developer", "engineer", "software engineer", "software developer")) return "GENERAL_IT";
        return "NON_IT";
    }

    private String normalizeTitle(String title) {
        if (title == null || title.isBlank()) return "UNKNOWN";

        String lower = title.toLowerCase(Locale.ROOT);

        if (containsAny(lower, "frontend", "front-end", "ui engineer", "web engineer")) return "Frontend Developer";
        if (containsAny(lower, "backend", "back-end")) return "Backend Developer";
        if (containsAny(lower, "full stack", "fullstack")) return "Full Stack Developer";
        if (containsAny(lower, "data engineer")) return "Data Engineer";
        if (containsAny(lower, "data scientist")) return "Data Scientist";
        if (containsAny(lower, "data analyst")) return "Data Analyst";
        if (containsAny(lower, "machine learning", "ml engineer")) return "ML Engineer";
        if (containsAny(lower, "devops", "site reliability", "sre")) return "DevOps Engineer";
        if (containsAny(lower, "android")) return "Android Developer";
        if (containsAny(lower, "ios")) return "iOS Developer";
        if (containsAny(lower, "qa engineer", "test engineer")) return "QA Engineer";

        return title;
    }

    private String extractCountryCode(String location) {
        if (location == null) return "UNKNOWN";

        if (containsAny(location, "japan", "tokyo", "osaka")) return "JP";
        if (containsAny(location, "united states", "usa", "us", "san francisco", "new york", "seattle")) return "US";
        if (containsAny(location, "singapore")) return "SG";

        return "UNKNOWN";
    }

    private String extractCity(String locationName) {
        if (locationName == null || locationName.isBlank()) return null;

        String lower = locationName.toLowerCase(Locale.ROOT);

        if (lower.contains("tokyo")) return "Tokyo";
        if (lower.contains("osaka")) return "Osaka";
        if (lower.contains("san francisco")) return "San Francisco";
        if (lower.contains("new york")) return "New York";
        if (lower.contains("seattle")) return "Seattle";
        if (lower.contains("singapore")) return "Singapore";

        return locationName;
    }

    private boolean containsAny(String source, String... keywords) {
        if (source == null) return false;

        for (String keyword : keywords) {
            if (source.contains(keyword)) return true;
        }
        return false;
    }

    private String safeLower(String value) {
        return value == null ? "" : value.toLowerCase(Locale.ROOT);
    }
}