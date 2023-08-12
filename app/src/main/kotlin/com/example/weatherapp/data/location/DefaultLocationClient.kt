package com.example.weatherapp.data.location

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Context
import android.content.IntentSender
import android.location.Location
import com.example.weatherapp.domain.extension.hasLocationPermission
import com.example.weatherapp.domain.location.LocationClient
import com.google.android.gms.common.api.ResolvableApiException
import com.google.android.gms.location.CurrentLocationRequest
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.Granularity
import com.google.android.gms.location.LocationRequest
import com.google.android.gms.location.LocationServices
import com.google.android.gms.location.LocationSettingsRequest
import com.google.android.gms.location.Priority
import com.google.android.gms.location.SettingsClient
import dagger.hilt.android.qualifiers.ActivityContext
import dagger.hilt.android.scopes.ActivityScoped
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

const val REQUEST_CODE_LOCATION_SETTINGS = 2242

@ActivityScoped
class DefaultLocationClient @Inject constructor(
  @ActivityContext private val context: Context,
  private val client: FusedLocationProviderClient,
) : LocationClient {
  @SuppressLint("MissingPermission")
  override fun getLocationUpdates(maxUpdateAge: Long): Flow<Location> {
    return callbackFlow {
      if (!context.applicationContext.hasLocationPermission()) {
        throw LocationClient.LocationException("Missing location permission")
      }

      val locationRequest = LocationRequest.Builder(5000)
        .setPriority(Priority.PRIORITY_BALANCED_POWER_ACCURACY)
        .build()

      val currentLocationRequest = CurrentLocationRequest.Builder()
        .setPriority(Priority.PRIORITY_BALANCED_POWER_ACCURACY)
        .setDurationMillis(1000)
        .setMaxUpdateAgeMillis(maxUpdateAge)
        .setGranularity(Granularity.GRANULARITY_PERMISSION_LEVEL)
        .build()

      val builder = LocationSettingsRequest.Builder()
        .addLocationRequest(locationRequest)

      val settingsClient: SettingsClient = LocationServices.getSettingsClient(context)
      settingsClient.checkLocationSettings(builder.build())
        .addOnSuccessListener { _ ->
          client.getCurrentLocation(
            currentLocationRequest,
            null
          ).addOnSuccessListener { location ->
            launch { send(location) }
          }.addOnFailureListener {
            throw it
          }
        }.addOnFailureListener { exception ->
          if (exception is ResolvableApiException) {
            // Location settings are not satisfied, but this can be fixed
            // by showing the user a dialog.
            try {
              // Show the dialog by calling startResolutionForResult(),
              // and check the result in onActivityResult().
              exception.startResolutionForResult(
                context as Activity,
                REQUEST_CODE_LOCATION_SETTINGS
              )
            } catch (sendEx: IntentSender.SendIntentException) {
              // Ignore the error.
            }
          }
        }

      awaitClose {}
    }
  }
}