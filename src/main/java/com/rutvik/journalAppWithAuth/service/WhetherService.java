package com.rutvik.journalAppWithAuth.service;

import com.rutvik.journalAppWithAuth.appCatch.AppCatch;
import com.rutvik.journalAppWithAuth.entity.weather.WeatherResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.Map;

@Service
public class WhetherService {
    @Autowired
    private RestTemplate restTemplate;
    @Autowired
    private AppCatch appCatch;
    //String api="http://api.weatherstack.com/current?access_key=API_KEY&query=CITY";
    //String api="https://wttr.in/CITY?format=j1";
    //private static final String API_KEY="b7c3553441eac7e8c3d946611346f365";
    public ResponseEntity<?> getWhether(String city){
        String name = SecurityContextHolder.getContext().getAuthentication().getName();

        //String finalApi = api.replace("CITY", city).replace("API_KEY", API_KEY);
        String finalApi = appCatch.appCatchMap.get(AppCatch.keys.WEATHER_API.toString()).replace("<city>", city);
        ResponseEntity<WeatherResponse> response = restTemplate.exchange(finalApi, HttpMethod.GET, null, WeatherResponse.class);
        WeatherResponse body=response.getBody();

        if (body == null || body.getCurrentConditions() == null || body.getCurrentConditions().isEmpty()) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to fetch weather data.");
        }

        String cityName = body.getNearestAreas().get(0).getAreaNames().get(0).getValue();
        String country = body.getNearestAreas().get(0).getCountry().get(0).getValue();
        String tempC = body.getCurrentConditions().get(0).getTemperatureCelsius();
        String tempF = String.valueOf(Integer.parseInt(tempC) * 9 / 5 + 32);
        String description = body.getCurrentConditions().get(0).getWeatherDescriptions().get(0).getValue();
        Map<String, String> result = new HashMap<>();
        result.put("name", name);
        result.put("city", cityName);
        result.put("country", country);
        result.put("temperature_celsius", tempC + "°C");
        result.put("temperature_fahrenheit", tempF + "°F");
        result.put("condition", description);
        return ResponseEntity.status(HttpStatus.OK).body(result);
    }
}
