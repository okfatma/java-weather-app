package com.fatmacan.weatherapp.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.Map;
import java.util.concurrent.CompletableFuture;

@Service
public class WeatherService {
    @Value("${weather.api.key}")
    private String apiKey;

    private final WebClient webClient;

    public WeatherService(WebClient.Builder webClientBuilder) {
        String apiUrl = "http://api.openweathermap.org/data/2.5";
        this.webClient = webClientBuilder.baseUrl(apiUrl).build();
    }

    public CompletableFuture<Map> getWeatherAsync(String city, String units) {
        return webClient.get()
                .uri(uriBuilder -> uriBuilder.path("/weather")
                        .queryParam("q", city)
                        .queryParam("units", units)
                        .queryParam("APPID", apiKey)
                        .build())
                .retrieve()
                .bodyToMono(java.util.Map.class)
                .map(response -> {
                    response.put("currentTime", System.currentTimeMillis());
                    return response;
                })
                .toFuture();
    }
}
