package com.currentweather.currentweather.model;

import java.util.Arrays;
import java.util.Optional;

public class OpenWeatherMapResponse {
    private Weather[] weather;

    public Optional<Weather> getWeather() {
        return Arrays.stream(weather).findFirst();
    }
}
