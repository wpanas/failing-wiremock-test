package com.github.wpanas.timeout;

import org.springframework.http.RequestEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.net.URI;

@Component
public class WebClient {
    private final RestTemplate restTemplate;
    private final ClientProperties clientProperties;

    public WebClient(RestTemplate restTemplate, ClientProperties clientProperties) {
        this.restTemplate = restTemplate;
        this.clientProperties = clientProperties;
    }

    public String get() {
        URI url = URI.create(clientProperties.getUrl());
        return restTemplate.getForObject(url, String.class);
    }
}
