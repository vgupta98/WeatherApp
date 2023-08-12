package com.example.weatherapp.domain.repository

import com.example.weatherapp.domain.model.Location
import com.example.weatherapp.domain.model.SearchResponse
import com.example.weatherapp.domain.network.DataState
import kotlinx.coroutines.flow.Flow

interface SearchRepository {
  fun search(query: String): Flow<DataState<List<SearchResponse>>>

  suspend fun getAllLocations(): List<Location>

  suspend fun removeLocation(id: Long)

  suspend fun checkIfLocationExists(name: String, region: String, lat: Double, lon: Double): Location?
}