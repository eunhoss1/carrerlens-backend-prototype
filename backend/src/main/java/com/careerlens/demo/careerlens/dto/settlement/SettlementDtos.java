package com.careerlens.demo.careerlens.dto.settlement;

public class SettlementDtos {
    public record ChecklistResponse(Long checklistId, String country, String timelineStage, String taskTitle, String referenceLink) {}
}
