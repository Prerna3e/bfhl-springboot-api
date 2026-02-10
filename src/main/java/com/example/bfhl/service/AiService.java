package com.example.bfhl.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.springframework.http.HttpMethod;

@Service
public class AiService {

    @Value("${gemini.api.key}")
    private String apiKey;

    private static final String GEMINI_URL = "https://generativelanguage.googleapis.com/v1beta/models/gemini-2.5-flash:generateContent?key=";

    public String getAiResponse(String question) {
        if (apiKey == null || apiKey.isEmpty()) {
            return "API Key not configured";
        }

        try {
            RestTemplate restTemplate = new RestTemplate();
            String url = GEMINI_URL + apiKey;

            // Request Body Construction
            Map<String, Object> part = new HashMap<>();
            part.put("text",
                    "Answer the following question with strictly one word. No punctuation. Question: " + question);

            Map<String, Object> content = new HashMap<>();
            content.put("parts", Collections.singletonList(part));

            Map<String, Object> requestBody = new HashMap<>();
            requestBody.put("contents", Collections.singletonList(content));

            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);

            HttpEntity<Map<String, Object>> entity = new HttpEntity<>(requestBody, headers);

            // Change postForEntity to exchange with ParameterizedTypeReference to get typed
            // response
            ResponseEntity<Map<String, Object>> response = restTemplate.exchange(
                    url,
                    HttpMethod.POST,
                    entity,
                    new org.springframework.core.ParameterizedTypeReference<Map<String, Object>>() {
                    });

            // Extracting the text from the response safely
            if (response.getStatusCode().is2xxSuccessful() && response.getBody() != null) {
                Map<String, Object> body = response.getBody();

                @SuppressWarnings("unchecked")
                List<Map<String, Object>> candidates = (List<Map<String, Object>>) body.get("candidates");

                if (candidates != null && !candidates.isEmpty()) {
                    Map<String, Object> candidate = candidates.get(0);

                    @SuppressWarnings("unchecked")
                    Map<String, Object> contentResponse = (Map<String, Object>) candidate.get("content");

                    if (contentResponse != null) {
                        @SuppressWarnings("unchecked")
                        List<Map<String, Object>> parts = (List<Map<String, Object>>) contentResponse.get("parts");

                        if (parts != null && !parts.isEmpty()) {
                            Map<String, Object> firstPart = parts.get(0);
                            return (String) firstPart.get("text");
                        }
                    }
                }
            }
            return "Failed to get response from Gemini";
        } catch (Exception e) {
            e.printStackTrace();
            return "Error calling AI Service: " + e.getMessage();
        }
    }
}
