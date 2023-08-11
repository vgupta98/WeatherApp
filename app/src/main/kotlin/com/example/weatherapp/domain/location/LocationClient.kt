package com.example.weatherapp.domain.location

import android.location.Location
import kotlinx.coroutines.flow.Flow

interface LocationClient {
  fun getLocationUpdates(maxUpdateAge: Long): Flow<Location>

  class LocationException(message: String) : Exception(message)
}