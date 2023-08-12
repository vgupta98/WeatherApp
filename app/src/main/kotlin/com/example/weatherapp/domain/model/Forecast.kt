package com.example.weatherapp.domain.model

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class Forecast(
  val forecastday: List<ForecastDay>
)

@JsonClass(generateAdapter = true)
data class ForecastDay(
  val date: String,
  val day: Day,
  val hour: List<HourForecast>,
  val astro: Astro,
)

@JsonClass(generateAdapter = true)
data class Day(
  @Json(name = "maxtemp_c")
  val maxtempC: Double,
  @Json(name = "mintemp_c")
  val mintemp_c: Double,
  val condition: Condition,
)

@JsonClass(generateAdapter = true)
data class HourForecast(
  val time: String,
  @Json(name = "temp_c")
  val tempC: Double,
  val condition: Condition,
)

@JsonClass(generateAdapter = true)
data class Astro(
  val sunrise: String,
  val sunset: String,
)



