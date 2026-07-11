package com.queuemgmt.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.Map;

// This talks to the separate ASP.NET Core notification microservice.
// If that service is not running, we just print an error and move on -
// the token generation should not fail just because a notification failed.
@Service
public class NotificationClientService {

    private final RestTemplate restTemplate;

    // URL of the .NET microservice, set in application.properties
    @Value("${notification.service.url}")
    private String notificationServiceUrl;

    public NotificationClientService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public boolean sendNotification(Long tokenId, String tokenNumber, String message) {
        try {
            Map<String, Object> body = new HashMap<>();
            body.put("tokenId", tokenId);
            body.put("tokenNumber", tokenNumber);
            body.put("message", message);

            // calling POST /api/notifications on the .NET service
            restTemplate.postForObject(notificationServiceUrl + "/api/notifications", body, String.class);
            return true;
        } catch (Exception e) {
            // student-style error handling - just log it, don't crash the app
            System.out.println("Could not reach notification service: " + e.getMessage());
            return false;
        }
    }
}
