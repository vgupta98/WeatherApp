package com.example.weatherapp.data.repository

import com.example.weatherapp.data.localdatabase.dao.LocationDao
import com.example.weatherapp.data.network.WeatherService
import com.example.weatherapp.domain.extension.parse
import com.example.weatherapp.domain.model.Location
import com.example.weatherapp.domain.network.DataState
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class WeatherRepository @Inject constructor(
  private val weatherService: WeatherService,
  private val locationDao: LocationDao,
) {
  fun getCurrentWeather(latLong: String) = flow {
    emit(DataState.Loading())
    emit(DataState.Success(weatherService.getCurrentWeather(latLong = latLong).parse()))
  }.catch {
    emit(DataState.Error(it))
  }

  fun getForecast(latLong: String) = flow {
    emit(DataState.Loading())
    emit(DataState.Success(weatherService.getForecast(latLong = latLong).parse()))
  }.catch {
    emit(DataState.Error(it))
  }

  suspend fun addLocation(location: Location) {
    locationDao.upsert(location)
  }

  suspend fun removeLocation(id: Long) {
    locationDao.deleteLocation(id)
  }
}