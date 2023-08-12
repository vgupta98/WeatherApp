package com.example.weatherapp.data.network

import com.example.weatherapp.domain.model.CurrentWeatherResponse
import com.example.weatherapp.domain.model.ForecastResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface WeatherService {
  @GET("/v1/current.json")
  suspend fun getCurrentWeather(
    @Query("q") latLong: String,
  ): Response<CurrentWeatherResponse>

  @GET("/v1/forecast.json")
  suspend fun getForecast(
    @Query("q") latLong: String,
    @Query("days") days: Int = 5,
  ): Response<ForecastResponse>
}