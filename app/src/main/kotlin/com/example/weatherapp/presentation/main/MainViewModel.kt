package com.example.weatherapp.presentation.main

import androidx.lifecycle.ViewModel
import com.example.weatherapp.domain.navigation.Navigator
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
  private val navigator: Navigator,
) : ViewModel() {
  val navigationChannel = navigator.navigationChannel
}