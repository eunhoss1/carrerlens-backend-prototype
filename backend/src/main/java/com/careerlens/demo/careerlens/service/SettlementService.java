package com.careerlens.demo.careerlens.service;

import com.careerlens.demo.careerlens.dto.settlement.SettlementDtos;
import com.careerlens.demo.careerlens.entity.UserProfile;
import com.careerlens.demo.careerlens.repository.SettlementChecklistRepository;
import com.careerlens.demo.careerlens.repository.UserProfileRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class SettlementService {
    private final SettlementChecklistRepository settlementChecklistRepository;
    private final UserProfileRepository userProfileRepository;

    public List<SettlementDtos.ChecklistResponse> byCountry(String country) {
        return settlementChecklistRepository.findByCountryIgnoreCase(country).stream()
                .map(c -> new SettlementDtos.ChecklistResponse(c.getId(), c.getCountry(), c.getTimelineStage(), c.getTaskTitle(), c.getReferenceLink())).toList();
    }

    public List<SettlementDtos.ChecklistResponse> byUser(Long userId) {
        UserProfile profile = userProfileRepository.findByUserId(userId).orElseThrow();
        return byCountry(profile.getTargetCountry());
    }
}
