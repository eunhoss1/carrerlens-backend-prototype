package com.carrerlens.demo.recommendation.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.Map;

@Service
public class LlmAnalysisService {

    @Value("${groq.api-key}")
    private String apiKey;

    @Value("${groq.base-url}")
    private String baseUrl;

    @Value("${groq.model}")
    private String model;

    private final RestTemplate restTemplate = new RestTemplate();

    public String analyzeJob(String desiredJobCategory,
                             String languageLevel,
                             String title,
                             String countryCode,
                             List<String> matchedSkills,
                             List<String> missingSkills) {

        try {
            String prompt = """
                    너는 해외취업 추천 도우미다.
                    아래 정보를 바탕으로 한국어로 3문장 이내로 설명해라.

                    반드시 아래 순서를 지켜라.
                    1) 왜 이 공고가 추천되는지
                    2) 부족한 점이 무엇인지
                    3) 무엇을 준비하면 좋은지

                    너무 장황하게 쓰지 말고, 실무적으로 써라.

                    [사용자 정보]
                    희망 직무: %s
                    언어 수준: %s

                    [공고 정보]
                    제목: %s
                    국가: %s
                    일치 스킬: %s
                    부족 스킬: %s
                    """.formatted(
                    safe(desiredJobCategory),
                    safe(languageLevel),
                    safe(title),
                    safe(countryCode),
                    matchedSkills == null ? "[]" : matchedSkills.toString(),
                    missingSkills == null ? "[]" : missingSkills.toString()
            );

            HttpHeaders headers = new HttpHeaders();
            headers.setBearerAuth(apiKey);
            headers.setContentType(MediaType.APPLICATION_JSON);

            Map<String, Object> requestBody = Map.of(
                    "model", model,
                    "messages", List.of(
                            Map.of("role", "system", "content", "응답은 짧고 실용적으로 작성하라."),
                            Map.of("role", "user", "content", prompt)
                    ),
                    "temperature", 0.4
            );

            HttpEntity<Map<String, Object>> requestEntity =
                    new HttpEntity<>(requestBody, headers);

            Map<?, ?> response = restTemplate.postForObject(
                    baseUrl + "/chat/completions",
                    requestEntity,
                    Map.class
            );

            if (response == null || response.get("choices") == null) {
                return "LLM 분석 결과를 가져오지 못했습니다.";
            }

            List<?> choices = (List<?>) response.get("choices");
            if (choices.isEmpty()) {
                return "LLM 분석 결과를 가져오지 못했습니다.";
            }

            Object firstChoice = choices.get(0);
            if (!(firstChoice instanceof Map<?, ?> choiceMap)) {
                return "LLM 분석 결과 형식이 올바르지 않습니다.";
            }

            Object messageObj = choiceMap.get("message");
            if (!(messageObj instanceof Map<?, ?> messageMap)) {
                return "LLM 메시지를 읽지 못했습니다.";
            }

            Object contentObj = messageMap.get("content");
            if (contentObj == null) {
                return "LLM 응답 본문이 비어 있습니다.";
            }

            return contentObj.toString().trim();

        } catch (Exception e) {
            return "LLM 분석 실패: " + e.getMessage();
        }
    }

    private String safe(String value) {
        return value == null ? "" : value;
    }
}