package com.rutvik.journalAppWithAuth.entity.weather;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

// Represents a current weather condition
public class CurrentCondition {

    @JsonProperty("FeelsLikeC")
    private String feelsLikeCelsius;

    @JsonProperty("temp_C")
    private String temperatureCelsius;

    @JsonProperty("weatherDesc")
    private List<WeatherDescription> weatherDescriptions;

    public String getFeelsLikeCelsius() {
        return feelsLikeCelsius;
    }

    public void setFeelsLikeCelsius(String feelsLikeCelsius) {
        this.feelsLikeCelsius = feelsLikeCelsius;
    }

    public String getTemperatureCelsius() {
        return temperatureCelsius;
    }

    public void setTemperatureCelsius(String temperatureCelsius) {
        this.temperatureCelsius = temperatureCelsius;
    }

    public List<WeatherDescription> getWeatherDescriptions() {
        return weatherDescriptions;
    }

    public void setWeatherDescriptions(List<WeatherDescription> weatherDescriptions) {
        this.weatherDescriptions = weatherDescriptions;
    }
}
