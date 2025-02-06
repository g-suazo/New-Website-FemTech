package com.femtech.empresa.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import java.util.Map;

@Service
public class RecaptchaService {

    @Value("${recaptcha.secret}")
    private String secretKey;

    private static final String VERIFY_URL = "https://www.google.com/recaptcha/api/siteverify";

    public boolean validateRecaptcha(String recaptchaResponse) {
        RestTemplate restTemplate = new RestTemplate();
        String requestUrl = VERIFY_URL + "?secret=" + secretKey + "&response=" + recaptchaResponse;

        Map<String, Object> response = restTemplate.postForObject(requestUrl, null, Map.class);

        if (response == null || !response.containsKey("success")) {
            return false;
        }

        return (boolean) response.get("success");
    }
}
