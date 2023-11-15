package com.example.demo.notification.service;

import com.example.demo.notification.dto.WeatherDto;
import com.example.demo.notification.dto.LocationAndDateFromMatching;

public interface WeatherService {

    WeatherDto getWeather(LocationAndDateFromMatching locationAndDateFromMatching);
}
