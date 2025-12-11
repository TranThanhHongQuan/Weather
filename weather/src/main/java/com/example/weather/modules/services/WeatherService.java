package com.example.weather.modules.services;

import com.example.weather.modules.dto.WeatherResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;
import org.springframework.http.ResponseEntity;

import java.util.Map;
import java.util.List;

@Service
@RequiredArgsConstructor
public class WeatherService {

    private final RestTemplate restTemplate;

    @Value("${weather.api.base-url}")
    private String baseUrl;

    @Value("${weather.api.key}")
    private String apiKey;

    public WeatherResponse getCurrentWeather(String city) {

        // ❗ Spring 6 - phải dùng fromUriString(), không còn fromHttpUrl()
        String url = UriComponentsBuilder.fromUriString(baseUrl + "/weather")
                .queryParam("q", city)
                .queryParam("appid", apiKey)
                .queryParam("units", "metric")  // nhiệt độ theo °C
                .toUriString();

        // Gửi request
        ResponseEntity<Map> response = restTemplate.getForEntity(url, Map.class);
        Map<String, Object> body = response.getBody();

        if (body == null) {
            throw new RuntimeException("Không nhận được dữ liệu từ API thời tiết");
        }

        // Parse dữ liệu trả về
        Map<String, Object> main = (Map<String, Object>) body.get("main");
        double temp = ((Number) main.get("temp")).doubleValue();
        double humidity = ((Number) main.get("humidity")).doubleValue();

        String description = "";
        List<Map<String, Object>> weatherList = (List<Map<String, Object>>) body.get("weather");

        if (weatherList != null && !weatherList.isEmpty()) {
            description = (String) weatherList.get(0).get("description");
        }

        String cityName = (String) body.get("name");

        return new WeatherResponse(
                cityName,
                description,
                temp,
                humidity
        );
    }
}
