package com.carrerlens.demo.job.service;

import com.carrerlens.demo.job.entity.JobPostingRaw;
import com.carrerlens.demo.job.eumtype.JobStatus;
import com.carrerlens.demo.job.repository.JobPostingRawRepository;
import com.carrerlens.demo.job.source.CollectedJobDto;
import com.carrerlens.demo.job.source.GreenhouseJobSourceFetcher;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class JobSyncService {

    private final GreenhouseJobSourceFetcher greenhouseJobSourceFetcher;
    private final JobPostingRawRepository jobPostingRawRepository;

    public JobSyncService(GreenhouseJobSourceFetcher greenhouseJobSourceFetcher,
                          JobPostingRawRepository jobPostingRawRepository) {
        this.greenhouseJobSourceFetcher = greenhouseJobSourceFetcher;
        this.jobPostingRawRepository = jobPostingRawRepository;
    }

    @Transactional
    public int syncRawJobs(String boardToken) {
        List<CollectedJobDto> jobs = greenhouseJobSourceFetcher.fetchJobs(boardToken);

        if (jobs == null || jobs.isEmpty()) {
            return 0;
        }

        int count = 0;

        for (CollectedJobDto item : jobs) {
            JobPostingRaw raw = jobPostingRawRepository
                    .findBySourceAndSourceJobId(item.getSourceType(), item.getSourceJobId())
                    .orElseGet(JobPostingRaw::new);

            raw.setSource(item.getSourceType());
            raw.setSourceJobId(item.getSourceJobId());
            raw.setTitle(item.getTitle());
            raw.setCompanyName(item.getCompanyName());
            raw.setLocationName(item.getLocationName());
            raw.setJobUrl(item.getJobUrl());
            raw.setContentRaw(item.getContentRaw());
            raw.setLanguageCode(item.getLanguageCode());
            raw.setUpdatedAtRaw(item.getUpdatedAtRaw());
            raw.setFirstPublishedRaw(item.getFirstPublishedRaw());
            raw.setDepartmentsRaw(item.getDepartmentsRaw());
            raw.setOfficesRaw(item.getOfficesRaw());
            raw.setStatus(JobStatus.OPEN);
            raw.setLastSeenAt(LocalDateTime.now());
            raw.setCollectedAt(LocalDateTime.now());

            jobPostingRawRepository.save(raw);
            count++;
        }

        return count;
    }
}