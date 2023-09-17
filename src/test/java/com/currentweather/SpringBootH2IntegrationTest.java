package com.currentweather;

import com.currentweather.model.WeatherRecord;
import com.currentweather.repository.WeatherRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(classes = CurrentWeatherApplication.class)
public class SpringBootH2IntegrationTest {

    @Autowired
    private WeatherRepository weatherRepository;

    @Test
    public void whenApplicationStarts_thenNoDataRecordExists() {
        Iterable<WeatherRecord> users = weatherRepository.findAll();

        assertThat(users)
                .hasSize(0);
    }

}
