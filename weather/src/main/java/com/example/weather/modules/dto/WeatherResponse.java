package com.example.weather.modules.dto;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
@Data
@NoArgsConstructor
@AllArgsConstructor
public class WeatherResponse {
    private String city;
    private String description;// mô tả trời mưa/nắng
    private double temperature;//ddo C
    private double humidity;
}
