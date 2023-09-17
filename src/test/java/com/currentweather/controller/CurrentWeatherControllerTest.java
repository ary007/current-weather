package com.currentweather.controller;

import com.currentweather.model.CurrentWeatherResponse;
import com.currentweather.model.WeatherRecord;
import com.currentweather.service.WeatherService;
import org.junit.jupiter.api.extension.ExtendWith;

import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.CoreMatchers.is;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
@ExtendWith(MockitoExtension.class)
public class CurrentWeatherControllerTest {

    @Autowired
    private MockMvc mvc;

    @MockBean
    private  WeatherService weatherService;

    @Test
    public void whenFetchCurrentWeather_thenReturnWeatherDescription() throws Exception {

       CurrentWeatherResponse currentWeatherResponse= new CurrentWeatherResponse();
        currentWeatherResponse.setDescription("test weather description");
        given(weatherService.findCurrentWeatherByCityAndCountry(any(), any()))
               .willReturn(currentWeatherResponse);

        mvc.perform(get("/api/weather/current")
                        .param("cityName", "Sydney")
                        .param("countryCode", "AU")
                        .header("X-API-KEY", "weather"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("description", is(currentWeatherResponse.getDescription())));
    }

    @Test
    public void whenFetchCurrentWeatherForInvalidCity_thenReturnWeatherDescription() throws Exception {

        given(weatherService.findCurrentWeatherByCityAndCountry(any(), any()))
                .willThrow(new NullPointerException("city not found"));

        mvc.perform(get("/api/weather/current")
                        .param("cityName", "Abcd")
                        .param("countryCode", "AU")
                        .header("X-API-KEY", "weather"))
                .andDo(print())
                .andExpect(status().isBadRequest())
                .andExpect(status().reason("Weather Not Found for Abcd, AU"));
    }

    @Test
    public void whenFetchWeatherRecords_thenReturnRecords() throws Exception {

        WeatherRecord weatherRecord=new WeatherRecord();
        weatherRecord.setCityName("Melbourne");
        weatherRecord.setCountryCode("AU");
        weatherRecord.setDescription("sunny");
        weatherRecord.setCreatedDateTime(LocalDateTime.now());

        List<WeatherRecord> weatherRecords = new ArrayList<>();
        weatherRecords.add(weatherRecord);

        given(weatherService.getAllWeatherRecords())
                .willReturn(weatherRecords);

        mvc.perform(get("/api/weather/records")
                        .header("X-API-KEY", "weather"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$[0].cityName", is(weatherRecord.getCityName())))
                .andExpect(jsonPath("$[0].countryCode", is(weatherRecord.getCountryCode())))
                .andExpect(jsonPath("$[0].description", is(weatherRecord.getDescription())))
                .andExpect(jsonPath("$[0].createdDateTime", is(weatherRecord.getCreatedDateTime().toString())));
    }

    @Test
    public void whenInValidAPIKey_thenReturnUnauthorised() throws Exception {
        mvc.perform(get("/api/weather/current")
                        .param("cityName", "Sydney")
                        .param("countryCode", "AU")
                        .header("X-API-KEY", "test"))
                .andDo(print())
                .andExpect(status().isUnauthorized())
                .andExpect(content().string("Invalid API Key"));
    }
}
