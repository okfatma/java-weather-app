import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { environment } from 'src/environments/environment';

@Injectable({
  providedIn: 'root'
})
export class WeatherService {

  constructor(private http: HttpClient,
  ) { }

  getWeather(city: string, unit: string): Observable<any> {
    return this.http.get(`${environment.apiUrl}/weather/q?city=${city}&units=${unit}`);
  }

  getForecast(city: string, unit: string, startDate: Date, endDate: Date): Observable<any> {
    const formattedStartDate = startDate.toISOString().split('T')[0];
    const formattedEndDate = endDate.toISOString().split('T')[0];

    return this.http.get(`${environment.apiUrl}/forecast/q?city=${city}&units=${unit}&startDate=${formattedStartDate}&endDate=${formattedEndDate}`);
  }
}
