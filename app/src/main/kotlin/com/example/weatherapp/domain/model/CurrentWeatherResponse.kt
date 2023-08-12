package com.example.weatherapp.domain.model

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class CurrentWeatherResponse(
    val current: Current,
    val location: Location
)

@JsonClass(generateAdapter = true)
data class Current(
    val cloud: Int,
    val condition: Condition,
    @Json(name = "feelslike_c")
    val feelslikeC: Double,
    @Json(name = "feelslike_f")
    val feelslikeF: Double,
    @Json(name = "gust_kph")
    val gustKph: Double,
    @Json(name = "gust_mph")
    val gustMph: Double,
    val humidity: Int,
    @Json(name = "is_day")
    val isDay: Int,
    @Json(name = "last_updated")
    val lastUpdated: String,
    @Json(name = "last_updated_epoch")
    val lastUpdatedEpoch: Int,
    @Json(name = "precip_in")
    val precipIn: Double,
    @Json(name = "precip_mm")
    val precipMm: Double,
    @Json(name = "pressure_in")
    val pressureIn: Double,
    @Json(name = "pressure_mb")
    val pressureMb: Double,
    @Json(name = "temp_c")
    val tempC: Double,
    @Json(name = "temp_f")
    val tempF: Double,
    val uv: Double,
    @Json(name = "vis_km")
    val visKm: Double,
    @Json(name = "vis_miles")
    val visMiles: Double,
    @Json(name = "wind_degree")
    val windDegree: Int,
    @Json(name = "wind_dir")
    val windDir: String,
    @Json(name = "wind_kph")
    val windKph: Double,
    @Json(name = "wind_mph")
    val windMph: Double
)

data class Location(
    val country: String,
    val lat: Double,
    val localtime: String,
    @Json(name = "localtime_epoch")
    val localtimeEpoch: Int,
    val lon: Double,
    val name: String,
    val region: String,
    @Json(name = "tz_id")
    val tzId: String
)

data class Condition(
    val code: Int,
    val icon: String,
    val text: String
)