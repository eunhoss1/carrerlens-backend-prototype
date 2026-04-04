package com.carrerlens.demo.job.source;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CollectedJobDto {
    private String sourceType;
    private Long sourceJobId;
    private String companyName;
    private String title;
    private String locationName;
    private String jobUrl;
    private String contentRaw;
    private String languageCode;
    private String updatedAtRaw;
    private String firstPublishedRaw;
    private String departmentsRaw;
    private String officesRaw;
}