package com.currentweather.currentweather.service;

import com.currentweather.currentweather.model.CurrentWeatherResponse;
import com.currentweather.currentweather.model.OpenWeatherMapResponse;
import com.currentweather.currentweather.model.WeatherRecord;
import com.currentweather.currentweather.repository.WeatherRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class WeatherService {

    final String appId = "9e716e08e4dad3ccd73d3c9b957d3b34";
    final String openWeatherMapBaseUri = "https://api.openweathermap.org/data/2.5";

    @Autowired
     private RestTemplate template;

    @Autowired
    private WeatherRepository weatherRepository;

    public CurrentWeatherResponse findCurrentWeatherByCityAndCountry(String cityName, String countryCode) {
        OpenWeatherMapResponse response = template.getForObject(openWeatherMapBaseUri+"/weather?q="+cityName+","+countryCode+"&appid="+appId, OpenWeatherMapResponse.class);
        CurrentWeatherResponse currentWeatherResponse = new CurrentWeatherResponse();
        assert response != null;
        assert response.getWeather().isPresent();
        String weatherDescription = response.getWeather().get().getDescription();
        currentWeatherResponse.setDescription(weatherDescription);
        WeatherRecord weatherRecord = new WeatherRecord();
        weatherRecord.setCityName(cityName);
        weatherRecord.setCountryCode(countryCode);
        weatherRecord.setDescription(weatherDescription);
        weatherRecord.setCreatedDateTime(LocalDateTime.now());
        weatherRepository.save(weatherRecord);
        return currentWeatherResponse;
    }

    public List<WeatherRecord> getAllWeatherRecords() {
        return weatherRepository.findAll();
    }

}
