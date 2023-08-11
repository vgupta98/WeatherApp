package com.example.weatherapp.presentation.search

import android.Manifest
import android.util.Log
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import com.example.weatherapp.data.location.DefaultLocationClient
import com.google.android.gms.location.LocationServices
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.cancel
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.timeout
import kotlinx.coroutines.launch
import kotlin.time.Duration.Companion.milliseconds

@OptIn(FlowPreview::class)
@Composable
fun SearchScreen(
  viewModel: SearchViewModel
) {

  var location by remember {
    mutableStateOf("")
  }
  val scope = rememberCoroutineScope()
  val context = LocalContext.current
  val locationClient = remember {
    DefaultLocationClient(
      context,
      LocationServices.getFusedLocationProviderClient(context.applicationContext)
    )
  }

  val locationPermissionRequest = rememberLauncherForActivityResult(
    ActivityResultContracts.RequestPermission()
  ) { isGranted ->
    if (isGranted) {
      Log.d("User Location", "Permission granted")
      scope.launch {
        locationClient
          .getLocationUpdates(20000)
          .timeout(2000.milliseconds)
          .catch { e ->
            Log.d("User Location", e.toString())
          }
          .onEach { fetchedLocation ->
            Log.d("User Location", "new location")
            val lat = fetchedLocation.latitude.toString()
            val long = fetchedLocation.longitude.toString()

            location = "$lat , $long"
          }
          .firstOrNull()
      }
    } else {
      // show toast
    }
  }

  DisposableEffect(Unit) {
    onDispose {
      scope.cancel()
    }
  }

  Column(
    modifier = Modifier.fillMaxSize(),
    horizontalAlignment = Alignment.CenterHorizontally,
    verticalArrangement = Arrangement.Center
  ) {
    Button(onClick = {
      locationPermissionRequest.launch(
        Manifest.permission.ACCESS_COARSE_LOCATION
      )
    }) {
      Text(text = "Get location")
    }

    Text(text = location)
  }
}