package com.femtech.empresa.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.Map;

@Service
public class RecaptchaValidator {

    @Value("${recaptcha.secret}")
    private String recaptchaSecret;

    private final String VERIFY_URL = "https://www.google.com/recaptcha/api/siteverify";

    public boolean isValid(String recaptchaToken) {
        RestTemplate restTemplate = new RestTemplate();
        Map<String, String> body = new HashMap<>();
        body.put("secret", recaptchaSecret);
        body.put("response", recaptchaToken);

        Map<String, Object> response = restTemplate.postForObject(VERIFY_URL, body, Map.class);
        return response != null && (Boolean) response.get("success");
    }
}
