package com.careerlens.demo.careerlens.service;

import com.careerlens.demo.careerlens.dto.planner.PlannerDtos;
import com.careerlens.demo.careerlens.entity.DiagnosisResult;
import com.careerlens.demo.careerlens.entity.PlannerRoadmap;
import com.careerlens.demo.careerlens.entity.PlannerTask;
import com.careerlens.demo.careerlens.entity.User;
import com.careerlens.demo.careerlens.repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class PlannerService {
    private final UserRepository userRepository;
    private final DiagnosisResultRepository diagnosisResultRepository;
    private final PlannerRoadmapRepository plannerRoadmapRepository;
    private final PlannerTaskRepository plannerTaskRepository;

    public PlannerDtos.RoadmapResponse generate(PlannerDtos.GenerateRoadmapRequest request) {
        User user = userRepository.findById(request.userId()).orElseThrow();
        DiagnosisResult diagnosis = diagnosisResultRepository.findById(request.diagnosisResultId()).orElseThrow();
        PlannerRoadmap roadmap = plannerRoadmapRepository.save(PlannerRoadmap.builder()
                .user(user)
                .diagnosisResult(diagnosis)
                .roadmapTitle("3단계 준비 로드맵")
                .build());
        plannerTaskRepository.save(PlannerTask.builder().roadmap(roadmap).phase(1).title("핵심 기술 보완").completed(false).build());
        plannerTaskRepository.save(PlannerTask.builder().roadmap(roadmap).phase(2).title("실전 경험 축적").completed(false).build());
        plannerTaskRepository.save(PlannerTask.builder().roadmap(roadmap).phase(3).title("서류 최적화").completed(false).build());
        return toResponse(roadmap);
    }

    public List<PlannerDtos.RoadmapResponse> getByUser(Long userId) {
        return plannerRoadmapRepository.findByUserId(userId).stream().map(this::toResponse).toList();
    }

    public PlannerDtos.TaskResponse updateTask(Long taskId, PlannerDtos.UpdateTaskRequest request) {
        PlannerTask task = plannerTaskRepository.findById(taskId).orElseThrow();
        task.setCompleted(request.completed());
        PlannerTask saved = plannerTaskRepository.save(task);
        return new PlannerDtos.TaskResponse(saved.getId(), saved.getPhase(), saved.getTitle(), saved.isCompleted());
    }

    private PlannerDtos.RoadmapResponse toResponse(PlannerRoadmap roadmap) {
        List<PlannerDtos.TaskResponse> tasks = plannerTaskRepository.findByRoadmapId(roadmap.getId())
                .stream().map(t -> new PlannerDtos.TaskResponse(t.getId(), t.getPhase(), t.getTitle(), t.isCompleted())).toList();
        return new PlannerDtos.RoadmapResponse(roadmap.getId(), roadmap.getRoadmapTitle(), tasks);
    }
}
