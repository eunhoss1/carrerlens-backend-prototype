package com.carrerlens.demo.job.client.greenhouse;


import com.carrerlens.demo.job.client.greenhouse.dto.GreenhouseJobsResponse;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;

@Service
public class GreenhouseClient {

    private final RestClient restClient;

    public GreenhouseClient(RestClient restClient) {
        this.restClient = restClient;
    }

    public GreenhouseJobsResponse fetchJobs(String boardToken) {
        String url = "https://boards-api.greenhouse.io/v1/boards/" + boardToken + "/jobs?content=true";

        return restClient.get()
                .uri(url)
                .retrieve()
                .body(GreenhouseJobsResponse.class);
    }
}