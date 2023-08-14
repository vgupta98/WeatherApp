package com.example.weatherapp.presentation.search

import android.Manifest
import android.util.Log
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.ime
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.example.weatherapp.R
import com.example.weatherapp.domain.location.LocationClient
import com.example.weatherapp.presentation.component.LifecycleCallbacks
import com.example.weatherapp.presentation.search.component.SavedLocationCard
import com.example.weatherapp.presentation.search.component.SearchBar
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.timeout
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.Date
import kotlin.time.Duration.Companion.milliseconds

@OptIn(FlowPreview::class)
@Composable
fun SearchScreen(
  locationClient: LocationClient,
  viewModel: SearchViewModel
) {

  LifecycleCallbacks(onResume = {
    viewModel.refreshSavedLocations()
  })

  val scope = rememberCoroutineScope()
  val isKeyboardVisible = WindowInsets.ime.getBottom(LocalDensity.current) > 0
  LaunchedEffect(key1 = isKeyboardVisible) {
    Log.d("SearchScreen", "SearchScreen: $isKeyboardVisible")
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
            // show toast
          }
          .onEach { fetchedLocation ->
            Log.d("User Location", "new location")
            val lat = fetchedLocation.latitude.toString()
            val long = fetchedLocation.longitude.toString()
            viewModel.navigateToWeatherScreen("$lat,$long")
            // location = "$lat,$long"
            // Log.d("User Location", location)
          }
          .firstOrNull()
      }
    } else {
      // show toast
    }
  }

  Column(
    modifier = Modifier
      .fillMaxSize()
      .background(Color.White)
  ) {
    Spacer(modifier = Modifier.size(123.dp))
    Text(
      text = "Weather App",
      style = MaterialTheme.typography.titleLarge,
      color = Color.Black,
      modifier = Modifier.padding(start = 16.dp)
    )
    Spacer(modifier = Modifier.size(37.dp))
    SearchBar(searchValue = viewModel.searchQuery, onSearchValueChange = viewModel::onQueryChange)

    when {
      isKeyboardVisible || viewModel.searchedLocations.isNotEmpty() -> {
        LazyColumn(
          modifier = Modifier.fillMaxWidth()
        ) {
          item {
            CurrentLocation {
              // get current location and navigate to weather screen
              locationPermissionRequest.launch(
                Manifest.permission.ACCESS_COARSE_LOCATION
              )
            }
          }
          items(
            count = viewModel.searchedLocations.size,
            key = { index ->
              viewModel.searchedLocations[index].id
            }
          ) { index ->
            SearchedLocationCard(
              locationName = viewModel.searchedLocations[index].name,
              regionName = viewModel.searchedLocations[index].region,
              countryName = viewModel.searchedLocations[index].country
            ) {
              // navigate to weather screen
              viewModel.navigateToWeatherScreen(
                latLong = "${viewModel.searchedLocations[index].lat},${viewModel.searchedLocations[index].lon}"
              )
            }
          }
        }
      }

      viewModel.savedLocations.isNotEmpty() -> {
        LazyColumn(
          modifier = Modifier.fillMaxWidth()
        ) {
          items(
            count = viewModel.savedLocations.size,
            key = { index ->
              viewModel.savedLocations[index].id
            }
          ) { index ->
            SavedLocationCard(
              locationName = viewModel.savedLocations[index].name,
              locationRegion = "${viewModel.savedLocations[index].region}, ${viewModel.savedLocations[index].country}",
              temperature = "${viewModel.savedLocations[index].lastTemperature}°c",
              weatherIconUrl = "http:${viewModel.savedLocations[index].iconUrl}",
              weatherTime = viewModel.savedLocations[index].localtimeEpoch.toFormattedTime(),
              onTap = {
                viewModel.navigateToWeatherScreen("${viewModel.savedLocations[index].lat},${viewModel.savedLocations[index].lon}")
              },
              onDelete = {
                viewModel.removeLocation(viewModel.savedLocations[index].id)
              }
            )
          }
        }
      }

      else -> {
        Column(
          modifier = Modifier.padding(horizontal = 56.dp),
          horizontalAlignment = Alignment.CenterHorizontally
        ) {
          Spacer(modifier = Modifier.size(84.dp))
          Icon(
            modifier = Modifier.size(88.dp),
            painter = painterResource(id = R.drawable.ic_weather_mix),
            contentDescription = null,
            tint = Color(0xFFE5E5E5)
          )
          Spacer(modifier = Modifier.size(28.dp))
          Text(
            textAlign = TextAlign.Center,
            text = "Search for a city or US/UK zip to check the weather",
            style = MaterialTheme.typography.labelMedium,
            color = Color(0xFFBEBEBE)
          )
        }
      }
    }
  }
}

@Composable
private fun SearchedLocationCard(
  locationName: String,
  regionName: String,
  countryName: String,
  onTap: () -> Unit
) {
  Column(
    modifier = Modifier
      .fillMaxWidth()
      .clickable { onTap() }
      .padding(start = 16.dp)
  ) {
    Spacer(modifier = Modifier.size(16.dp))
    Text(
      text = "$locationName, $regionName, $countryName",
      style = MaterialTheme.typography.labelMedium,
      color = Color.Black
    )
    Spacer(modifier = Modifier.size(14.dp))
    Spacer(
      modifier = Modifier
        .fillMaxWidth()
        .height(1.dp)
        .background(Color(0xFFEFEFEF))
    )
  }
}

@Composable
private fun CurrentLocation(
  onTap: () -> Unit,
) {
  Row(
    verticalAlignment = Alignment.CenterVertically,
    modifier = Modifier
      .clickable { onTap() }
      .padding(16.dp)
  ) {
    Icon(
      painter = painterResource(id = R.drawable.ic_near_me),
      contentDescription = null,
      tint = Color(0xFF3B60E4),
      modifier = Modifier.size(16.dp)
    )
    Spacer(modifier = Modifier.size(6.dp))
    Text(
      text = "Current Location",
      color = Color(0xFF3B60E4),
      style = MaterialTheme.typography.labelMedium
    )
  }
}

private val sdf = SimpleDateFormat("h:mm a")

private fun Long.toFormattedTime(): String {
  // todo: implement getting a formatted time
  val date = Date(this * 1000)
  return sdf.format(date)
  // return "5 a.m."
}