package com.fatmacan.weatherapp.mapper;

import com.fatmacan.weatherapp.model.dto.request.CityRequest;
import com.fatmacan.weatherapp.model.dto.response.CityResponse;
import com.fatmacan.weatherapp.model.entity.City;
import org.springframework.stereotype.Component;

@Component
public class CityMapper {
    public CityResponse toCityResponse(City city) {
        if (city == null) return null;

        return new CityResponse(city.getId(), city.getName());
    }

    public City toCity(City city, CityRequest request) {
        if (city == null || request == null) return null;

        return new City(city.getId(), request.name());
    }

    public City toCity(CityRequest request) {
        if (request == null) return null;

        return new City(request.name());
    }
}
