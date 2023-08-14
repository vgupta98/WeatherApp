package com.example.weatherapp.presentation.weather

import android.util.Log
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.weatherapp.domain.model.CurrentWeatherResponse
import com.example.weatherapp.domain.model.ForecastResponse
import com.example.weatherapp.domain.model.Location
import com.example.weatherapp.domain.navigation.Destination
import com.example.weatherapp.domain.navigation.Navigator
import com.example.weatherapp.domain.network.DataState.Error
import com.example.weatherapp.domain.network.DataState.Loading
import com.example.weatherapp.domain.network.DataState.Success
import com.example.weatherapp.domain.repository.WeatherRepository
import com.example.weatherapp.presentation.weather.component.DayForecastUiModel
import com.example.weatherapp.presentation.weather.component.HourForecastUiModel
import com.example.weatherapp.presentation.weather.component.toDayForecastUiModel
import com.example.weatherapp.presentation.weather.component.toHourForecastUiModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import java.util.Calendar
import javax.inject.Inject
import kotlin.math.min

private val calendar: Calendar = Calendar.getInstance()

@HiltViewModel
class WeatherViewModel @Inject constructor(
  private val weatherRepository: WeatherRepository,
  private val navigator: Navigator,
  savedStateHandle: SavedStateHandle
) : ViewModel() {
  private val latLong = savedStateHandle[Destination.Weather.LAT_LONG] ?: ""

  var currentWeatherResponse by mutableStateOf<CurrentWeatherResponse?>(null)
    private set

  var forecastWeatherResponse by mutableStateOf<ForecastResponse?>(null)
    private set

  var savedLocationId: Long? by mutableStateOf(null)
    private set

  val isLoading by derivedStateOf { currentWeatherResponse == null || forecastWeatherResponse == null }

  init {
    weatherRepository.getCurrentWeather(latLong = latLong).onEach {
      when (it) {
        is Error -> Log.d("WeatherResult", it.error.toString())
        is Loading -> {}
        is Success -> {
          currentWeatherResponse = it.data
          checkIfLocationExists(
            name = currentWeatherResponse?.location?.name.toString(),
            region = currentWeatherResponse?.location?.region.toString(),
            lat = currentWeatherResponse?.location?.lat ?: 0.0,
            lon = currentWeatherResponse?.location?.lon ?: 0.0
          )
        }
      }
    }.launchIn(viewModelScope)
    weatherRepository.getForecast(latLong = latLong).onEach {
      when (it) {
        is Error -> Log.d("WeatherResult", it.error.toString())
        is Loading -> {}
        is Success -> {
          forecastWeatherResponse = it.data
        }
      }
    }.launchIn(viewModelScope)
  }

  private suspend fun checkIfLocationExists(
    name: String, region: String, lat: Double, lon: Double
  ) {
    savedLocationId = weatherRepository.checkIfLocationExists(
      name, region, lat, lon
    )?.id
  }

  fun addRemoveLocation() {
    currentWeatherResponse?.location?.let {
      if (savedLocationId == null) {
        addLocation(it)
      } else {
        removeLocation(savedLocationId ?: 0)
      }
    }
  }

  private fun addLocation(
    location: Location,
  ) {
    viewModelScope.launch {
      savedLocationId = weatherRepository.addLocation(
        location = location.copy(
          lastTemperature = currentWeatherResponse?.current?.tempC.toString(),
          iconUrl = currentWeatherResponse?.current?.condition?.icon.toString()
        )
      )
    }
  }

  private fun removeLocation(
    locationId: Long
  ) {
    viewModelScope.launch {
      weatherRepository.removeLocation(locationId)
      savedLocationId = null
    }
  }

  fun getHourForecasts(): List<HourForecastUiModel> {
    val currentHour = forecastWeatherResponse?.current?.lastUpdatedEpoch?.getCurrentHour() ?: 0
    val forecasts = forecastWeatherResponse?.forecast?.forecastday?.get(0)?.hour
    Log.d("getHourForecasts", "getHourForecasts: ${forecasts?.size}")
    Log.d("getHourForecasts", "currentHour: ${currentHour}")
    val hourForecasts: MutableList<HourForecastUiModel>? =
      forecasts?.slice(currentHour..min(forecasts.size - 1, currentHour + 6))?.map {
        it.toHourForecastUiModel()
      }?.toMutableList()

    hourForecasts?.let {
      hourForecasts[0] = hourForecasts[0].copy(
        hour = "Now"
      )
    }

    return hourForecasts.orEmpty()
  }

  fun getDayForecasts(): List<DayForecastUiModel> {
    return forecastWeatherResponse?.forecast?.forecastday?.map { it.toDayForecastUiModel() }
      .orEmpty()
  }

  fun navigateBack() {
    navigator.tryNavigateBack()
  }
}

fun Long.getCurrentHour(): Int {
  calendar.timeInMillis = this * 1000
  Log.d("getCurrentHour", "getCurrentHour: ${calendar.get(Calendar.HOUR_OF_DAY)}")
  return calendar.get(Calendar.HOUR_OF_DAY)
}

fun Long.getCurrentWeekDay(): String {

  calendar.timeInMillis = this * 1000
  val dayOfWeek = calendar.get(Calendar.DAY_OF_WEEK)

  val dayNames = arrayOf("Sun", "Mon", "Tues", "Wed", "Thur", "Fri", "Sat")
  return dayNames[dayOfWeek - Calendar.SUNDAY]
}