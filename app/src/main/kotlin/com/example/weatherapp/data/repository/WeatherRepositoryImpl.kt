package com.example.weatherapp.data.repository

import com.example.weatherapp.data.localdatabase.dao.LocationDao
import com.example.weatherapp.data.network.WeatherService
import com.example.weatherapp.domain.extension.parse
import com.example.weatherapp.domain.model.Location
import com.example.weatherapp.domain.network.DataState
import com.example.weatherapp.domain.repository.WeatherRepository
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class WeatherRepositoryImpl @Inject constructor(
  private val weatherService: WeatherService,
  private val locationDao: LocationDao,
) : WeatherRepository {
  override fun getCurrentWeather(latLong: String) = flow {
    emit(DataState.Loading())
    emit(DataState.Success(weatherService.getCurrentWeather(latLong = latLong).parse()))
  }.catch {
    emit(DataState.Error(it))
  }

  override fun getForecast(latLong: String) = flow {
    emit(DataState.Loading())
    emit(DataState.Success(weatherService.getForecast(latLong = latLong).parse()))
  }.catch {
    emit(DataState.Error(it))
  }

  override suspend fun addLocation(location: Location) =
    locationDao.upsert(location)

  override suspend fun removeLocation(id: Long) {
    locationDao.deleteLocation(id)
  }

  override suspend fun checkIfLocationExists(
    name: String,
    region: String,
    lat: Double,
    lon: Double
  ) = locationDao.getLocationByParams(name, region, lat, lon).firstOrNull()
}