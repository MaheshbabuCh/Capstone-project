package com.example.capstoneproject.clients.authenticationclient;

import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

@Component
@RestController
public class AuthenticationClient {

    RestTemplateBuilder restTemplateBuilder;

    public AuthenticationClient(RestTemplateBuilder restTemplateBuilder) {
        this.restTemplateBuilder = restTemplateBuilder;
    }

    public boolean validate(long userId, String token) {

        ValidationRequestDto validationRequest = new ValidationRequestDto();

        validationRequest.setUserId(userId);
        validationRequest.setToken(token);

        RestTemplate restTemplate = restTemplateBuilder.build();

        try{
            ResponseEntity<ValidationResponseDto> resp = restTemplate.postForEntity("http://localhost:8000/auth/validate", validationRequest, ValidationResponseDto.class);


            if (resp.getStatusCode().is2xxSuccessful() && resp.getBody() != null) {
                return resp.getBody().isSuccess();
            }
            return false;

        } catch (HttpClientErrorException e) {
            if (e.getStatusCode() == HttpStatus.UNAUTHORIZED) {
                // validation failed -> treat as invalid
                return false;
            }
            // rethrow other client errors
            return false;
        }

    }







}
