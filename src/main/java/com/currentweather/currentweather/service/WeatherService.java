package com.currentweather.currentweather.service;

import com.currentweather.currentweather.model.CurrentWeatherResponse;
import com.currentweather.currentweather.model.OpenWeatherMapResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
@Service
public class WeatherService {

    final String appId = "9e716e08e4dad3ccd73d3c9b957d3b34";
    final String openWeatherMapBaseUri = "https://api.openweathermap.org/data/2.5";

    @Autowired
     private RestTemplate template;


    public CurrentWeatherResponse findCurrentWeatherByCityAndCountry(String cityName, String countryCodee) {
    OpenWeatherMapResponse response = template.getForObject(openWeatherMapBaseUri+"/weather?q="+cityName+","+countryCodee+"&appid="+appId, OpenWeatherMapResponse.class);
    CurrentWeatherResponse currentWeatherResponse = new CurrentWeatherResponse();
    assert response != null;
    assert response.getWeather().isPresent();
    currentWeatherResponse.setDescription(response.getWeather().get().getDescription());
    return currentWeatherResponse;
}
}
