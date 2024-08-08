import { Component, Inject, LOCALE_ID, OnInit } from '@angular/core';
import { WeatherService } from '../services/weather.service';
import { CityService } from '../services/city.service';
import { City } from '../models/city';
import { Unit } from '../models/unit';
import { FormatWidth, getLocaleDateFormat } from '@angular/common';
;

@Component({
  selector: 'app-weather',
  templateUrl: './weather.component.html',
  styleUrls: ['./weather.component.css']
})
export class WeatherComponent implements OnInit {
  city: string;
  unit: string; // 'metric' for Celsius, 'imperial' for Fahrenheit
  weather: any;
  forecast: any;
  cities: City[];
  units: Unit[];


  constructor(private cityService: CityService, private weatherService: WeatherService, @Inject(LOCALE_ID) private locale: string) { }

  ngOnInit(): void {
    this.getUnits();
    this.getCities();
    //this.getForecast();
  }

  getUnits() {
    this.units = [
      { name: 'metric', displayName: 'Celsius' },
      { name: 'imperial', displayName: 'Fahrenheit' }
    ];
    this.unit = this.units[0].name;
  }

  getCities() {
    this.cityService.getCities().subscribe(data => {
      this.cities = data;
      if(this.cities) {
        this.city = this.cities[0].name;
      }
      this.getWeather();
    });
  }

  getWeather(): void {
    if(!this.city) {
      console.error("İl seçiniz");
      return;
    }

    this.weatherService.getWeather(this.city, this.unit).subscribe(data => {
      this.weather = data;
    });
  }

  getForecast(): void {
    this.weatherService.getForecast(this.city, this.unit).subscribe(data => {
      this.forecast = data;
    });
  }

  onCityChange(newCity: string): void {
    this.city = newCity;
    this.getWeather();
    this.getForecast();
  }

  onUnitChange(newUnit: string): void {
    this.unit = newUnit;
    this.getWeather();
    this.getForecast();
  }

  getDate(currentTime: string) {
    let formattedDate = new Date(currentTime).toLocaleDateString("TR-tr", {
      year: 'numeric',
      month: '2-digit',
      day: '2-digit',
      hour: '2-digit',
      minute: '2-digit',
      hour12: false
    });
    return formattedDate;
  }

}
