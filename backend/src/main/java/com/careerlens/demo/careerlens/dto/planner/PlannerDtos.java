package com.careerlens.demo.careerlens.dto.planner;

import java.util.List;

public class PlannerDtos {
    public record GenerateRoadmapRequest(Long userId, Long diagnosisResultId) {}
    public record TaskResponse(Long taskId, Integer phase, String title, boolean completed) {}
    public record RoadmapResponse(Long roadmapId, String roadmapTitle, List<TaskResponse> tasks) {}
    public record UpdateTaskRequest(boolean completed) {}
}
