package com.example.secondlife.common.llm.service;

import com.example.secondlife.common.llm.dto.GPTRequest;
import com.example.secondlife.common.llm.dto.GPTResponse;
import com.example.secondlife.common.llm.dto.Message;
import java.util.ArrayList;
import java.util.Collections;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
@RequiredArgsConstructor
public class GPTService {

    private final RestTemplate restTemplate;

    @Value("${gpt.model}")
    private String model;

    @Value("${gpt.api.url}")
    private String apiUrl;

    @Value("${gpt.api.system}")
    private String system;

    public GPTResponse getGPTResponse(String prompt) {
        final String finalPrompt = system + prompt;

        GPTRequest request = GPTRequest.builder()
                .model(model)
                .messages(new ArrayList<>(Collections.singletonList(
                        new Message("user", finalPrompt)
                )))
                .temperature(1)
                .maxTokens(300)
                .topP(1)
                .frequencyPenalty(0)
                .presencePenalty(0)
                .build();

        return restTemplate.postForObject(
                apiUrl
                , request
                , GPTResponse.class
        );

    }
}
