package com.fatmacan.weatherapp.controller;

import com.fatmacan.weatherapp.service.WeatherService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;
import java.util.concurrent.CompletableFuture;

@RestController
@RequestMapping("/weather")
public class WeatherController {
    private final WeatherService service;

    public WeatherController(WeatherService service) {
        this.service = service;
    }

    @GetMapping("/q")
    public CompletableFuture<Map> getWeather(@RequestParam String city, @RequestParam String units) {
        return service.getWeatherAsync(city, units);
    }
}
