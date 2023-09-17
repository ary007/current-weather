package com.currentweather.controller;

import com.currentweather.model.CurrentWeatherResponse;
import com.currentweather.model.WeatherRecord;
import com.currentweather.service.WeatherService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController
@RequestMapping("/api")
public class WeatherController {

    @Autowired
    private WeatherService weatherService;

    @GetMapping("/weather/current")
    public ResponseEntity<CurrentWeatherResponse> hello(@RequestParam("cityName") String cityName, @RequestParam("countryCode") String countryCode) {
        try {
            return ResponseEntity.status(HttpStatus.OK).body(weatherService.findCurrentWeatherByCityAndCountry(cityName, countryCode));
        }
        catch(Exception ex) {
            if(ex.getMessage().contains("city not found")) {
                throw new ResponseStatusException(
                        HttpStatus.BAD_REQUEST, "Weather Not Found for %s, %s".formatted(cityName, countryCode));
            }
            throw new ResponseStatusException(
                    HttpStatus.INTERNAL_SERVER_ERROR, "Reason unknown");
        }
    }

    @GetMapping("/weather/records")
    public ResponseEntity<List<WeatherRecord>> allWeatherRecords() {
        return ResponseEntity.status(HttpStatus.OK).body(weatherService.getAllWeatherRecords());
    }

    @GetMapping("/isAlive")
    public String isAlive() {
        return "Weather App is alive, you don't need authentication to check whether I am alive";
    }
}
