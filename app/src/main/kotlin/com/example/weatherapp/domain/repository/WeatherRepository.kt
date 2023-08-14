package com.example.weatherapp.domain.repository

import com.example.weatherapp.domain.model.CurrentWeatherResponse
import com.example.weatherapp.domain.model.ForecastResponse
import com.example.weatherapp.domain.model.Location
import com.example.weatherapp.domain.network.DataState
import kotlinx.coroutines.flow.Flow

interface WeatherRepository {
  fun getCurrentWeather(latLong: String): Flow<DataState<CurrentWeatherResponse>>

  fun getForecast(latLong: String): Flow<DataState<ForecastResponse>>

  suspend fun addLocation(location: Location): Long

  suspend fun removeLocation(id: Long)

  suspend fun checkIfLocationExists(
    name: String,
    region: String,
    lat: Double,
    lon: Double
  ): Location?
}