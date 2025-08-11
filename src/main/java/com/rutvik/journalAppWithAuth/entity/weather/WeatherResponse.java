package com.rutvik.journalAppWithAuth.entity.weather;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

public class WeatherResponse {

    @JsonProperty("current_condition")
    private List<CurrentCondition> currentConditions;

    @JsonProperty("nearest_area")
    private List<NearestArea> nearestAreas;

    public List<CurrentCondition> getCurrentConditions() {
        return currentConditions;
    }

    public void setCurrentConditions(List<CurrentCondition> currentConditions) {
        this.currentConditions = currentConditions;
    }

    public List<NearestArea> getNearestAreas() {
        return nearestAreas;
    }

    public void setNearestAreas(List<NearestArea> nearestAreas) {
        this.nearestAreas = nearestAreas;
    }
}

