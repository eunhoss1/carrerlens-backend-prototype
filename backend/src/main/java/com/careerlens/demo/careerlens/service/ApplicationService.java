package com.careerlens.demo.careerlens.service;

import com.careerlens.demo.careerlens.dto.application.ApplicationDtos;
import com.careerlens.demo.careerlens.entity.ApplicationRecord;
import com.careerlens.demo.careerlens.entity.Enums;
import com.careerlens.demo.careerlens.entity.JobPosting;
import com.careerlens.demo.careerlens.entity.User;
import com.careerlens.demo.careerlens.repository.ApplicationRecordRepository;
import com.careerlens.demo.careerlens.repository.JobPostingRepository;
import com.careerlens.demo.careerlens.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ApplicationService {
    private final ApplicationRecordRepository applicationRecordRepository;
    private final UserRepository userRepository;
    private final JobPostingRepository jobPostingRepository;

    public ApplicationDtos.ApplicationResponse create(ApplicationDtos.CreateApplicationRequest request) {
        User user = userRepository.findById(request.userId()).orElseThrow();
        JobPosting job = jobPostingRepository.findById(request.jobPostingId()).orElseThrow();
        ApplicationRecord saved = applicationRecordRepository.save(ApplicationRecord.builder()
                .user(user)
                .jobPosting(job)
                .resumeKeywords(request.resumeKeywords())
                .portfolioStatus(request.portfolioStatus())
                .status(Enums.ApplicationStatus.PREPARING)
                .build());
        return toResponse(saved);
    }

    public List<ApplicationDtos.ApplicationResponse> getByUser(Long userId) {
        return applicationRecordRepository.findByUserId(userId).stream().map(this::toResponse).toList();
    }

    public ApplicationDtos.ApplicationResponse updateStatus(Long id, ApplicationDtos.UpdateApplicationStatusRequest request) {
        ApplicationRecord record = applicationRecordRepository.findById(id).orElseThrow();
        record.setStatus(Enums.ApplicationStatus.valueOf(request.status()));
        return toResponse(applicationRecordRepository.save(record));
    }

    private ApplicationDtos.ApplicationResponse toResponse(ApplicationRecord a) {
        return new ApplicationDtos.ApplicationResponse(a.getId(), a.getUser().getId(), a.getJobPosting().getId(), a.getJobPosting().getTitle(), a.getJobPosting().getCompany(), a.getStatus().name(), a.getResumeKeywords(), a.getPortfolioStatus());
    }
}
