package com.careerlens.demo.careerlens.dto.application;

public class ApplicationDtos {
    public record CreateApplicationRequest(Long userId, Long jobPostingId, String resumeKeywords, String portfolioStatus) {}
    public record ApplicationResponse(Long applicationId, Long userId, Long jobPostingId, String title, String company, String status, String resumeKeywords, String portfolioStatus) {}
    public record UpdateApplicationStatusRequest(String status) {}
}
