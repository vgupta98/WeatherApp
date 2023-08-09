package com.example.weatherapp.presentation.search

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.weatherapp.data.repository.SearchRepository
import com.example.weatherapp.domain.network.DataState.Error
import com.example.weatherapp.domain.network.DataState.Loading
import com.example.weatherapp.domain.network.DataState.Success
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

@HiltViewModel
class SearchViewModel @Inject constructor(
  private val searchRepository: SearchRepository
) : ViewModel() {
  init {
    searchRepository.search("Lond").onEach {
      when (it) {
        is Error -> Log.d("SearchResult", it.error.toString())
        is Loading -> {}
        is Success -> Log.d("SearchResult", it.data.toString())
      }

    }.launchIn(viewModelScope)
  }
}