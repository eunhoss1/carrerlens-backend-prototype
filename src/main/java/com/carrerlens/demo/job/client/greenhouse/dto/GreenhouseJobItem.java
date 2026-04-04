package com.carrerlens.demo.job.client.greenhouse.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class GreenhouseJobItem {
    private Long id;
    private String title;
    private String company_name;
    private String updated_at;
    private String first_published;
    private String absolute_url;
    private String content;
    private String language;
    private GreenhouseLocationDto location;
    private List<GreenhouseDepartmentDto> departments;
    private List<GreenhouseOfficeDto> offices;
}