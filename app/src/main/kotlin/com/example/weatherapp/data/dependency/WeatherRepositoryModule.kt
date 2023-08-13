package com.example.weatherapp.data.dependency

import com.example.weatherapp.data.localdatabase.dao.LocationDao
import com.example.weatherapp.data.network.WeatherService
import com.example.weatherapp.data.repository.WeatherRepositoryImpl
import com.example.weatherapp.domain.repository.WeatherRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.scopes.ViewModelScoped

@Module
@InstallIn(ViewModelComponent::class)
object WeatherRepositoryModule {

  @ViewModelScoped
  @Provides
  fun provideWeatherRepository(
    weatherService: WeatherService,
    locationDao: LocationDao,
  ): WeatherRepository = WeatherRepositoryImpl(
    weatherService, locationDao
  )
}