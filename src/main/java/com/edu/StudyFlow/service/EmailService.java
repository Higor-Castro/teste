package com.edu.StudyFlow.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.Map;

@Service
public class EmailService {

    @Value("${brevo.api-key}")
    private String apiKey;

    @Value("${brevo.email}")
    private String emailRemetente;

    private final RestTemplate restTemplate = new RestTemplate();

    public void enviarEmail(String destinatario, String corpo) {

        String url = "https://api.brevo.com/v3/smtp/email";

        HttpHeaders headers = new HttpHeaders();

        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.set("api-key", apiKey);

        Map<String, Object> remetente = new HashMap<>();
        remetente.put("email", emailRemetente);
        remetente.put("name", "StudyFlow");

        Map<String, Object> destinatarioMap = new HashMap<>();
        destinatarioMap.put("email", destinatario);

        Map<String, Object> body = new HashMap<>();
        body.put("sender", remetente);
        body.put("to", new Map[]{destinatarioMap});
        body.put("subject", "StudyFlow");
        body.put("textContent", corpo);

        HttpEntity<Map<String, Object>> request =
                new HttpEntity<>(body, headers);

        restTemplate.postForEntity(
                url,
                request,
                String.class
        );
    }
}