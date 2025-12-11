package com.example.weather.modules.controller;

import com.example.weather.modules.dto.WeatherResponse;
import com.example.weather.modules.services.WeatherService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequiredArgsConstructor
public class WeatherPageController {

    private final WeatherService weatherService;

    // Trang form nhập city
    @GetMapping("/weather")
    public String showWeatherPage(
            @RequestParam(required = false) String city,
            Model model
    ) {
        // Nếu chưa nhập city -> chỉ hiện form
        if (city != null && !city.isBlank()) {
            try {
                WeatherResponse weather = weatherService.getCurrentWeather(city);
                model.addAttribute("weather", weather);
            } catch (Exception ex) {
                model.addAttribute("error", "Không lấy được dữ liệu thời tiết: " + ex.getMessage());
            }
        }
        return "weather"; // trỏ tới file templates/weather.html
    }
}