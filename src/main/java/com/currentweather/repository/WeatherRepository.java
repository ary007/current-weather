package com.currentweather.repository;

import com.currentweather.model.WeatherRecord;
import org.springframework.data.jpa.repository.JpaRepository;

public interface WeatherRepository extends JpaRepository<WeatherRecord, Long> {
}