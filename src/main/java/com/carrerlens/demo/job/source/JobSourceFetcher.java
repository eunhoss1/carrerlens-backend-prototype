package com.carrerlens.demo.job.source;

import java.util.List;

public interface JobSourceFetcher {
    SourceType supports();
    List<CollectedJobDto> fetchJobs(String sourceKey);
}