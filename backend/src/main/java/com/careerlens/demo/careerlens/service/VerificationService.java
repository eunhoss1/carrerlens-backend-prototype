package com.careerlens.demo.careerlens.service;

import com.careerlens.demo.careerlens.dto.verification.VerificationDtos;
import com.careerlens.demo.careerlens.entity.*;
import com.careerlens.demo.careerlens.repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class VerificationService {
    private final VerificationRequestRepository verificationRequestRepository;
    private final VerificationBadgeRepository verificationBadgeRepository;
    private final UserRepository userRepository;

    public VerificationDtos.VerificationResponse create(VerificationDtos.CreateVerificationRequest request) {
        User user = userRepository.findById(request.userId()).orElseThrow();
        VerificationRequest saved = verificationRequestRepository.save(VerificationRequest.builder()
                .user(user)
                .evidenceUrl(request.evidenceUrl())
                .status(Enums.VerificationStatus.PENDING)
                .build());
        return toResponse(saved);
    }

    public List<VerificationDtos.VerificationResponse> byUser(Long userId) {
        return verificationRequestRepository.findByUserId(userId).stream().map(this::toResponse).toList();
    }

    public List<VerificationDtos.VerificationResponse> recruiterList() {
        return verificationRequestRepository.findByStatus(Enums.VerificationStatus.APPROVED).stream().map(this::toResponse).toList();
    }

    public VerificationDtos.VerificationResponse adminUpdate(Long id, VerificationDtos.UpdateVerificationStatusRequest request) {
        VerificationRequest vr = verificationRequestRepository.findById(id).orElseThrow();
        Enums.VerificationStatus status = Enums.VerificationStatus.valueOf(request.status());
        vr.setStatus(status);
        vr.setAdminComment(request.adminComment());
        VerificationRequest saved = verificationRequestRepository.save(vr);

        if (status == Enums.VerificationStatus.APPROVED) {
            VerificationBadge badge = verificationBadgeRepository.findByUserId(vr.getUser().getId())
                    .orElse(VerificationBadge.builder().user(vr.getUser()).badgeName("Verified Profile").active(true).build());
            badge.setActive(true);
            verificationBadgeRepository.save(badge);
        }
        return toResponse(saved);
    }

    private VerificationDtos.VerificationResponse toResponse(VerificationRequest v) {
        String badge = verificationBadgeRepository.findByUserId(v.getUser().getId()).filter(VerificationBadge::isActive).map(VerificationBadge::getBadgeName).orElse(null);
        return new VerificationDtos.VerificationResponse(v.getId(), v.getUser().getId(), v.getUser().getName(), v.getStatus().name(), v.getAdminComment(), badge);
    }
}
