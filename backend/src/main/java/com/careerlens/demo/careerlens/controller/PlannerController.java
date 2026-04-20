package com.careerlens.demo.careerlens.controller;

import com.careerlens.demo.careerlens.common.ApiResponse;
import com.careerlens.demo.careerlens.dto.planner.PlannerDtos;
import com.careerlens.demo.careerlens.service.PlannerService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/planner")
@RequiredArgsConstructor
public class PlannerController {
    private final PlannerService plannerService;

    @PostMapping("/generate")
    public ApiResponse<PlannerDtos.RoadmapResponse> generate(@RequestBody PlannerDtos.GenerateRoadmapRequest request) {
        return ApiResponse.ok("Planner generated", plannerService.generate(request));
    }

    @GetMapping("/{userId}")
    public ApiResponse<List<PlannerDtos.RoadmapResponse>> get(@PathVariable Long userId) {
        return ApiResponse.ok("Planner fetched", plannerService.getByUser(userId));
    }

    @PatchMapping("/task/{taskId}")
    public ApiResponse<PlannerDtos.TaskResponse> updateTask(@PathVariable Long taskId, @RequestBody PlannerDtos.UpdateTaskRequest request) {
        return ApiResponse.ok("Task updated", plannerService.updateTask(taskId, request));
    }
}
