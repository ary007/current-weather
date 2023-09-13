package com.currentweather.currentweather.controller;

import com.currentweather.currentweather.model.CurrentWeatherResponse;
import com.currentweather.currentweather.service.WeatherService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.beans.factory.annotation.Autowired;

@RestController
@RequestMapping("/api")
public class WeatherController {

    @Autowired
    private WeatherService weatherService;

    @GetMapping("/currentWeather")
    public ResponseEntity<CurrentWeatherResponse> hello(@RequestParam("cityName") String cityName, @RequestParam("countryCode") String countryCode) {
        return ResponseEntity.status(HttpStatus.OK).body(weatherService.findCurrentWeatherByCityAndCountry(cityName, countryCode));
    }
}
