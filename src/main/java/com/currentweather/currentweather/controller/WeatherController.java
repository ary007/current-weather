package com.currentweather.currentweather.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class WeatherController {
    @GetMapping("/currentWeather")
    public ResponseEntity<String> hello() {
        return ResponseEntity.status(HttpStatus.OK).body("hello");
    }
}
