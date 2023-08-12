package com.example.weatherapp.domain.model

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class SearchResponse(
    val country: String,
    val id: Long,
    val lat: Double,
    val lon: Double,
    val name: String,
    val region: String,
    val url: String
)