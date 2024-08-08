package com.fatmacan.weatherapp.controller;

import com.fatmacan.weatherapp.service.ForecastService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Map;
import java.util.concurrent.CompletableFuture;

@RestController
@RequestMapping("/forecast")
public class ForecastController {
    private final ForecastService service;

    public ForecastController(ForecastService service) {
        this.service = service;
    }

    @GetMapping("/q")
    public CompletableFuture<ArrayList<Map>> getWeather(@RequestParam String city, @RequestParam String units, @RequestParam LocalDate startDate, @RequestParam LocalDate endDate) {
        return service.getForecast(city, units, startDate, endDate);
    }
}
