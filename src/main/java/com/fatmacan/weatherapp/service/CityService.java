package com.fatmacan.weatherapp.service;

import com.fatmacan.weatherapp.mapper.CityMapper;
import com.fatmacan.weatherapp.model.dto.request.CityRequest;
import com.fatmacan.weatherapp.model.dto.response.CityResponse;
import com.fatmacan.weatherapp.model.entity.City;
import com.fatmacan.weatherapp.repository.CityRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CityService {
    private final CityMapper mapper;
    private final CityRepository repository;

    public CityService(CityMapper mapper, CityRepository repository) {
        this.mapper = mapper;
        this.repository = repository;
    }

    public List<CityResponse> getAll() {
        return repository.findAll().stream().map(mapper::toCityResponse).toList();
    }

    public CityResponse getCityById(Long id) {
        var city = repository.findById(id);

        return city.map(mapper::toCityResponse).orElse(null);
    }

    public CityResponse getCityByName(String name) {
        var message = String.format("There is no city with the name %s", name);

        return repository.findByNameIgnoreCase(name).map(mapper::toCityResponse)
                .orElseThrow(() -> new EntityNotFoundException(message));
    }

    public CityResponse saveCity(CityRequest request) {
        var city = mapper.toCity(request);

        return mapper.toCityResponse(repository.save(city));
    }

    public CityResponse updateCity(Long id, CityRequest request) {
        var city = findCityById(id);
        var updatedCity = mapper.toCity(city, request);

        return mapper.toCityResponse(repository.save(updatedCity));
    }

    public void deleteCity(Long id) {
        var city = findCityById(id);
        repository.delete(city);
    }

    private City findCityById(Long id) {
        var message = String.format("There is no city with the id %d", id);

        return repository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException(message));
    }
}
