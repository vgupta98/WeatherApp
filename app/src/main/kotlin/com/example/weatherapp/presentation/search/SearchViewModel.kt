package com.example.weatherapp.presentation.search

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.weatherapp.domain.model.Location
import com.example.weatherapp.domain.model.SearchResponse
import com.example.weatherapp.domain.navigation.Destination
import com.example.weatherapp.domain.navigation.Navigator
import com.example.weatherapp.domain.network.DataState.Error
import com.example.weatherapp.domain.network.DataState.Loading
import com.example.weatherapp.domain.network.DataState.Success
import com.example.weatherapp.domain.repository.SearchRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@OptIn(FlowPreview::class)
@HiltViewModel
class SearchViewModel @Inject constructor(
  private val searchRepository: SearchRepository,
  private val navigator: Navigator,
) : ViewModel() {

  var searchQuery by mutableStateOf("")
    private set
  var savedLocations = mutableStateListOf<Location>()
    private set
  var searchedLocations = mutableStateListOf<SearchResponse>()
    private set
  private val queryChannel = Channel<String>()

  init {
    viewModelScope.launch {
      queryChannel
        .receiveAsFlow()
        .onEach {
          if (it.isEmpty()) searchedLocations.clear()
        }
        .debounce(500)
        .filter { query ->
          query.length > 2
        }
        .collect {
          Log.d("SearchViewModel", "SearchViewModel: $it")
          searchRepository.search(it).collect { response ->
            when (response) {
              is Error -> {}
              is Loading -> {}
              is Success -> {
                searchedLocations.clear()
                searchedLocations.addAll(response.data ?: listOf())
              }
            }
          }
        }
    }
    // refreshSavedLocations()
  }

  fun refreshSavedLocations() {
    viewModelScope.launch {
      savedLocations.clear()
      savedLocations.addAll(searchRepository.getAllLocations())
    }
  }

  fun onQueryChange(newQuery: String) {
    searchQuery = newQuery
    viewModelScope.launch {
      queryChannel.send(newQuery)
    }
  }

  fun navigateToWeatherScreen(latLong: String) {
    navigator.tryNavigateTo(route = Destination.Weather(latLong = latLong))
  }

  fun removeLocation(locationId: Long) {
    viewModelScope.launch {
      searchRepository.removeLocation(locationId)
      savedLocations.removeIf { it.id == locationId }
    }
  }
}