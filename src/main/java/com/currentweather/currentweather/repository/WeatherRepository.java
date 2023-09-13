package com.currentweather.currentweather.repository;

import com.currentweather.currentweather.model.WeatherRecord;
import org.springframework.data.jpa.repository.JpaRepository;

public interface WeatherRepository extends JpaRepository<WeatherRecord, Long> {
}