package com.carrerlens.demo.job.controller;

import com.carrerlens.demo.job.client.greenhouse.GreenhouseClient;
import com.carrerlens.demo.job.client.greenhouse.dto.GreenhouseJobsResponse;
import com.carrerlens.demo.job.dto.JobSkillResponse;
import com.carrerlens.demo.job.service.JobNormalizationService;
import com.carrerlens.demo.job.service.JobSkillExtractionService;
import com.carrerlens.demo.job.service.JobSyncService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/test/jobs")
public class JobTestController {

    private final GreenhouseClient greenhouseClient;
    private final JobSyncService jobSyncService;
    private final JobNormalizationService jobNormalizationService;
    private final JobSkillExtractionService jobSkillExtractionService;

    public JobTestController(GreenhouseClient greenhouseClient,
                             JobSyncService jobSyncService,
                             JobNormalizationService jobNormalizationService,
                             JobSkillExtractionService jobSkillExtractionService) {
        this.greenhouseClient = greenhouseClient;
        this.jobSyncService = jobSyncService;
        this.jobNormalizationService = jobNormalizationService;
        this.jobSkillExtractionService = jobSkillExtractionService;
    }

    @GetMapping("/board/{boardToken}")
    public GreenhouseJobsResponse fetchJobs(@PathVariable String boardToken) {
        return greenhouseClient.fetchJobs(boardToken);
    }

    @GetMapping("/{jobId}/skills")
    public JobSkillResponse getJobSkills(@PathVariable Long jobId) {
        return jobSkillExtractionService.getJobSkills(jobId);
    }

    @PostMapping("/sync/{boardToken}")
    public String syncJobs(@PathVariable String boardToken) {
        int savedCount = jobSyncService.syncRawJobs(boardToken);
        return savedCount + "개의 공고를 raw 테이블에 저장했습니다.";
    }

    @PostMapping("/normalize")
    public String normalizeJobs() {
        int count = jobNormalizationService.normalizeOpenJobs();
        return count + "개의 공고를 normalized 테이블에 반영했습니다.";
    }

    @PostMapping("/extract-skills")
    public String extractSkills() {
        int count = jobSkillExtractionService.extractSkillsFromItJobs();
        return count + "개의 공고-기술스택 매핑을 저장했습니다.";
    }
}