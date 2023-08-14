package com.example.weatherapp.data.repository

import com.example.weatherapp.data.localdatabase.dao.LocationDao
import com.example.weatherapp.data.network.SearchService
import com.example.weatherapp.domain.extension.parse
import com.example.weatherapp.domain.network.DataState
import com.example.weatherapp.domain.repository.SearchRepository
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class SearchRepositoryImpl @Inject constructor(
  private val searchService: SearchService,
  private val locationDao: LocationDao
) : SearchRepository {
  override fun search(query: String) =
    flow {
      emit(DataState.Loading())
      emit(DataState.Success(searchService.search(query).parse()))
    }.catch {
      emit(DataState.Error(it))
    }

  override suspend fun getAllLocations() =
    locationDao.getAllLocations()

  override suspend fun removeLocation(id: Long) {
    locationDao.deleteLocation(id)
  }
}