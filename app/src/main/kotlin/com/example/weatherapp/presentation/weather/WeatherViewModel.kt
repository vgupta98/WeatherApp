package com.example.weatherapp.presentation.weather

import android.util.Log
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.weatherapp.data.repository.WeatherRepository
import com.example.weatherapp.domain.navigation.Destination
import com.example.weatherapp.domain.network.DataState.Error
import com.example.weatherapp.domain.network.DataState.Loading
import com.example.weatherapp.domain.network.DataState.Success
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

@HiltViewModel
class WeatherViewModel @Inject constructor(
  private val weatherRepository: WeatherRepository,
  savedStateHandle: SavedStateHandle
) : ViewModel() {
  private val latLong = savedStateHandle[Destination.Weather.LAT_LONG] ?: ""

  init {
    weatherRepository.getCurrentWeather(latLong = latLong).onEach {
      when (it) {
        is Error -> Log.d("WeatherResult", it.error.toString())
        is Loading -> {}
        is Success -> Log.d("WeatherResult", it.data.toString())
      }
    }.launchIn(viewModelScope)
  }
}