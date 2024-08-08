package com.fatmacan.weatherapp.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import java.time.LocalDate;
import java.time.ZoneOffset;
import java.util.*;
import java.util.concurrent.CompletableFuture;

@Service
public class ForecastService {
    @Value("${weather.api.key}")
    private String apiKey;

    private final WebClient webClient;

    public ForecastService(WebClient.Builder webClientBuilder) {
        String apiUrl = "http://api.openweathermap.org/data/2.5";
        this.webClient = webClientBuilder.baseUrl(apiUrl).build();
    }

    public CompletableFuture<ArrayList<Map>> getForecast(String city, String units, LocalDate startDate, LocalDate endDate) {
        return webClient.get()
                .uri(uriBuilder -> uriBuilder.path("/weather")
                        .queryParam("q", city)
                        .queryParam("units", units)
                        .queryParam("APPID", apiKey)
                        .build())
                .retrieve()
                .bodyToMono(Map.class)
                .map(response -> {
                    var forecastList = new ArrayList<Map>();
                    List<LocalDate> dateList = getDatesBetween(startDate, endDate);
                    Random random = new Random();

                    // Seçilen tarih aralığında random -+5 değer üretildi, veritabanından çekilebilir.
                    for (LocalDate date : dateList) {
                        Map main = (Map) response.get("main");
                        var temp = (double)main.get("temp") + random.nextInt(11) - 5;
                        var forecast = new HashMap<String, String>();
                        forecast.put("temp", String.valueOf(temp));
                        forecast.put("time", String.valueOf(date.atStartOfDay().toInstant(ZoneOffset.UTC).toEpochMilli()));
                        forecastList.add(forecast);
                        System.out.println(date);
                    }
                    return forecastList;
                })
                .toFuture();
    }

    private List<LocalDate> getDatesBetween(LocalDate startDate, LocalDate endDate) {
        List<LocalDate> dates = new ArrayList<>();
        LocalDate currentDate = startDate;

        while (!currentDate.isAfter(endDate)) {
            dates.add(currentDate);
            currentDate = currentDate.plusDays(1);
        }

        return dates;
    }
}
