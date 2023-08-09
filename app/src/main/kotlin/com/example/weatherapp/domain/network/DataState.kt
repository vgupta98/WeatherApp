package com.example.weatherapp.domain.network

sealed class DataState<T>(
  val data: T? = null,
  val error: String? = null,
  val isLoading: Boolean = true
) {
  class Success<T>(response: T) : DataState<T>(data = response, isLoading = false)
  class Loading<T> : DataState<T>(isLoading = true)
  class Error<T>(throwable: Throwable) : DataState<T>(
    error = throwable.toString(),
    isLoading = false
  )
}