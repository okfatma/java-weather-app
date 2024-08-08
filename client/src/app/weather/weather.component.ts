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
  dateRange: any;
  lineChartData: any;
  lineChartOptions: any;


  constructor(private cityService: CityService, private weatherService: WeatherService, @Inject(LOCALE_ID) private locale: string) { }

  ngOnInit(): void {
    this.initChartOptions();
    this.getUnits();
    this.getCities();
    //this.getForecast();
  }

  initChartOptions() {
    this.lineChartOptions = {
      responsive: true,
      plugins: {
        legend: {
          position: 'top',
        },
        title: {
          display: true,
          text: 'Temperature Over Time'
        }
      },
      scales: {
        x: {
          type: 'time',
          time: {
            unit: 'day'
          }
        },
        y: {
          beginAtZero: true
        }
      }
    };
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
      if (this.cities) {
        this.city = this.cities[0].name;
      }
      this.getWeather();
    });
  }

  getWeather(): void {
    if (!this.city) {
      console.error("İl seçiniz");
      return;
    }

    this.weatherService.getWeather(this.city, this.unit).subscribe(data => {
      this.weather = data;
    });
  }

  getWeatherForecast(): void {
    let startDate = new Date(this.dateRange[0]);
    startDate.setDate(startDate.getDate() + 1);

    let endDate = new Date(this.dateRange[1]);
    endDate.setDate(endDate.getDate() + 1);

    this.weatherService.getForecast(this.city, this.unit, startDate, endDate).subscribe(data => {
      if (!data) {
        this.forecast = undefined;
        console.log("No data found for the selected date range");
        return;
      }

      this.lineChartData = {
        labels: data.map(forecast => this.getDate(forecast.time)),
        datasets: [
          {
            label: 'Hava Durumu Tahmini',
            data: data.map(forecast => parseFloat(forecast.temp)),
            fill: false,
            borderColor: '#42A5F5',
            tension: 0.4
          }
        ]
      };

      this.forecast = data;
    });
  }

  onCityChange(newCity: string): void {
    this.city = newCity;
    this.getWeather();
  }

  onUnitChange(newUnit: string): void {
    this.unit = newUnit;
    this.getWeather();
  }

  getDate(currentTime: string) {
    let formattedDate = new Date(parseInt(currentTime)).toLocaleDateString("TR-tr", {
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
