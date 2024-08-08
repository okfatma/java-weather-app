package com.fatmacan.weatherapp;

import com.fatmacan.weatherapp.mapper.CityMapper;
import com.fatmacan.weatherapp.model.entity.City;
import com.fatmacan.weatherapp.repository.CityRepository;
import com.fatmacan.weatherapp.service.CityService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.Arrays;

import static org.assertj.core.api.AssertionsForInterfaceTypes.assertThat;
import static org.mockito.Mockito.when;

@SpringBootTest
public class CityServiceTest {
    private CityService cityService;

    @MockBean
    private CityMapper cityMapper;

    @MockBean
    private CityRepository cityRepository;

    @BeforeEach
    void setUp() {
        cityService = new CityService(cityMapper, cityRepository);
    }

    @Test
    void getAllCitiesTest() {
        var city1 = new City(1L, "Istanbul");
        var city2 = new City(2L, "New York");
        var city3 = new City(3L, "New Delhi");

        when(cityRepository.findAll()).thenReturn(Arrays.asList(city1, city2, city3));

        assertThat(cityService.getAll()).hasSize(3);
    }
}