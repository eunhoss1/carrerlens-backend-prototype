package com.careerlens.demo.careerlens.dto.verification;

public class VerificationDtos {
    public record CreateVerificationRequest(Long userId, String evidenceUrl) {}
    public record VerificationResponse(Long verificationId, Long userId, String userName, String status, String adminComment, String badgeName) {}
    public record UpdateVerificationStatusRequest(String status, String adminComment) {}
}
