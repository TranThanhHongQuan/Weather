package com.example.weather.modules.controller;

import com.example.weather.modules.dto.WeatherResponse;
import com.example.weather.modules.services.WeatherService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/weather")
@RequiredArgsConstructor
public class WeatherController {

    private final WeatherService weatherService;

    @GetMapping("/current")
    public WeatherResponse getCurrentWeather(@RequestParam String city) {
        return weatherService.getCurrentWeather(city);
    }
}