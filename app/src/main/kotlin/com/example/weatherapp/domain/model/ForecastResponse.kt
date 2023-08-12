package com.example.weatherapp.domain.model

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class ForecastResponse(
  val location: Location,
  val current: Current,
  val forecast: Forecast,
  val alerts: AlertList,
)


@JsonClass(generateAdapter = true)
data class AlertList(
  val alert: List<Alert>,
)

@JsonClass(generateAdapter = true)
data class Alert(
  val headline: String,
  val event: String,
  val effective: String,
  val expires: String,
  val desc: String,
)