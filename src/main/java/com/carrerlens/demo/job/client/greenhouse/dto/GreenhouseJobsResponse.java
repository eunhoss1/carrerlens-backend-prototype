package com.carrerlens.demo.job.client.greenhouse.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class GreenhouseJobsResponse {
    private List<GreenhouseJobItem> jobs;
}
