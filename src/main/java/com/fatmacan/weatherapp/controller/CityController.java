package com.fatmacan.weatherapp.controller;

import com.fatmacan.weatherapp.model.dto.request.CityRequest;
import com.fatmacan.weatherapp.model.dto.response.CityResponse;
import com.fatmacan.weatherapp.service.CityService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin
@RestController
@RequestMapping("/cities")
public class CityController {
    private final CityService service;

    public CityController(CityService service) {this.service = service;}

    @GetMapping
    public List<CityResponse> findAll() {
        return service.getAll();
    }

    @GetMapping("/{id}")
    public CityResponse findById(@PathVariable Long id) {
        return service.getCityById(id);
    }

    @GetMapping("/q")
    public CityResponse findByName(@RequestParam String name) {
        return service.getCityByName(name);
    }

    @PostMapping
    public CityResponse save(@RequestBody CityRequest request) {
        return service.saveCity(request);
    }

    @PutMapping("/{id}")
    public CityResponse update(@PathVariable Long id, @RequestBody CityRequest request) {
        return service.updateCity(id, request);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        service.deleteCity(id);
    }
}
