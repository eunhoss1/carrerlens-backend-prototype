package com.carrerlens.demo.job.source;

import com.carrerlens.demo.job.client.greenhouse.GreenhouseClient;

import com.carrerlens.demo.job.client.greenhouse.dto.GreenhouseDepartmentDto;
import com.carrerlens.demo.job.client.greenhouse.dto.GreenhouseJobItem;
import com.carrerlens.demo.job.client.greenhouse.dto.GreenhouseJobsResponse;
import com.carrerlens.demo.job.client.greenhouse.dto.GreenhouseOfficeDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class GreenhouseJobSourceFetcher implements JobSourceFetcher {

    private final GreenhouseClient greenhouseClient;

    @Override
    public SourceType supports() {
        return SourceType.GREENHOUSE;
    }

    @Override
    public List<CollectedJobDto> fetchJobs(String boardToken) {
        GreenhouseJobsResponse response = greenhouseClient.fetchJobs(boardToken);

        if (response == null || response.getJobs() == null) {
            return Collections.emptyList();
        }

        return response.getJobs().stream()
                .map(this::toCollectedJobDto)
                .toList();
    }

    private CollectedJobDto toCollectedJobDto(GreenhouseJobItem item) {
        CollectedJobDto dto = new CollectedJobDto();
        dto.setSourceType("greenhouse");
        dto.setSourceJobId(item.getId());
        dto.setCompanyName(item.getCompany_name());
        dto.setTitle(item.getTitle());
        dto.setLocationName(item.getLocation() != null ? item.getLocation().getName() : null);
        dto.setJobUrl(item.getAbsolute_url());
        dto.setContentRaw(item.getContent());
        dto.setLanguageCode(item.getLanguage());
        dto.setUpdatedAtRaw(item.getUpdated_at());
        dto.setFirstPublishedRaw(item.getFirst_published());
        dto.setDepartmentsRaw(joinDepartments(item));
        dto.setOfficesRaw(joinOffices(item));
        return dto;
    }

    private String joinDepartments(GreenhouseJobItem item) {
        if (item.getDepartments() == null || item.getDepartments().isEmpty()) {
            return null;
        }

        return item.getDepartments().stream()
                .map(GreenhouseDepartmentDto::getName)
                .filter(Objects::nonNull)
                .collect(Collectors.joining(", "));
    }

    private String joinOffices(GreenhouseJobItem item) {
        if (item.getOffices() == null || item.getOffices().isEmpty()) {
            return null;
        }

        return item.getOffices().stream()
                .map(GreenhouseOfficeDto::getName)
                .filter(Objects::nonNull)
                .collect(Collectors.joining(", "));
    }
}