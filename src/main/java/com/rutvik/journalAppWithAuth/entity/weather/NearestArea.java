package com.rutvik.journalAppWithAuth.entity.weather;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

// Describes the location (nearest city and country)
public class NearestArea {

    @JsonProperty("areaName")
    private List<AreaName> areaNames;

    private List<Country> country;

    public List<AreaName> getAreaNames() {
        return areaNames;
    }

    public void setAreaNames(List<AreaName> areaNames) {
        this.areaNames = areaNames;
    }

    public List<Country> getCountry() {
        return country;
    }

    public void setCountry(List<Country> country) {
        this.country = country;
    }
}
