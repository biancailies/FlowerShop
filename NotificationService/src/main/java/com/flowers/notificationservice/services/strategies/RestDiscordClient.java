package com.flowers.notificationservice.services.strategies;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.Map;

@Component
public class RestDiscordClient implements DiscordClient {

    private final RestTemplate restTemplate;
    private final String apiBaseUrl;

    public RestDiscordClient(
            RestTemplateBuilder restTemplateBuilder,
            @Value("${notification.discord.api-base-url:https://discord.com/api/v10}") String apiBaseUrl) {
        this.restTemplate = restTemplateBuilder.build();
        this.apiBaseUrl = apiBaseUrl;
    }

    @Override
    public void sendMessage(String botToken, String channelId, String message) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.set("Authorization", "Bot " + botToken);

        Map<String, String> body = new HashMap<>();
        body.put("content", message);

        String url = apiBaseUrl + "/channels/" + channelId + "/messages";
        restTemplate.postForEntity(url, new HttpEntity<>(body, headers), String.class);
    }
}
