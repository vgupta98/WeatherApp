package com.example.weatherapp.data.repository

import com.example.weatherapp.data.network.SearchService
import com.example.weatherapp.domain.extension.parse
import com.example.weatherapp.domain.network.DataState
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class SearchRepository @Inject constructor(
  private val searchService: SearchService
) {
  fun search(query: String) =
    flow {
      emit(DataState.Loading())
      emit(DataState.Success(searchService.search(query).parse()))
    }.catch {
      emit(DataState.Error(it))
    }
}