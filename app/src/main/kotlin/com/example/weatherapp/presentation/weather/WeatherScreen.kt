package com.example.weatherapp.presentation.weather

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.airbnb.lottie.compose.LottieAnimation
import com.airbnb.lottie.compose.LottieCompositionSpec
import com.airbnb.lottie.compose.LottieConstants
import com.airbnb.lottie.compose.rememberLottieComposition
import com.example.weatherapp.R
import com.example.weatherapp.presentation.weather.component.AlertCard
import com.example.weatherapp.presentation.weather.component.CurrentWeatherCard
import com.example.weatherapp.presentation.weather.component.DayForecastCard
import com.example.weatherapp.presentation.weather.component.HourForecastCard
import com.example.weatherapp.presentation.weather.component.PrecipitationCard
import com.example.weatherapp.presentation.weather.component.SunsetSunriseCard
import com.example.weatherapp.presentation.weather.component.UVCard
import com.example.weatherapp.presentation.weather.component.WindCard

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun WeatherScreen(
  viewModel: WeatherViewModel
) {
  val composition by rememberLottieComposition(LottieCompositionSpec.RawRes(R.raw.loading_lottie))
  if (viewModel.isLoading) {
    Box(
      modifier = Modifier.fillMaxSize()
    ) {
      LottieAnimation(
        composition, modifier = Modifier
          .align(Alignment.Center),
        iterations = LottieConstants.IterateForever
      )
    }
  } else {
    LazyColumn(
      modifier = Modifier
        .fillMaxSize()
        .background(Color.White)
        .padding(horizontal = 16.dp)
    ) {
      stickyHeader {
        Spacer(
          modifier = Modifier
            .height(30.dp)
            .fillMaxWidth()
            .background(Color.White)
        )
        AppBar(
          isAdded = viewModel.savedLocationId != null,
          onBackPress = viewModel::navigateBack,
          onAddRemoveButtonClick = viewModel::addRemoveLocation
        )
      }
      item {
        Spacer(modifier = Modifier.size(4.dp))
        CurrentWeatherCard(
          locationName = viewModel.currentWeatherResponse?.location?.name.toString(),
          regionName = viewModel.currentWeatherResponse?.location?.region.toString(),
          countryName = viewModel.currentWeatherResponse?.location?.country.toString(),
          currentTemperature = viewModel.currentWeatherResponse?.current?.tempC.toString(),
          highTemperature = "${viewModel.forecastWeatherResponse?.forecast?.forecastday?.get(0)?.day?.maxtempC}°c",
          lowTemperature = "${viewModel.forecastWeatherResponse?.forecast?.forecastday?.get(0)?.day?.mintempC}°c",
          weatherIconUrl = "http:${viewModel.currentWeatherResponse?.current?.condition?.icon.toString()}",
          feelsLikeTemperature = "${viewModel.currentWeatherResponse?.current?.feelslikeC}°c"
        )
        Spacer(modifier = Modifier.size(26.dp))
      }
      item {
        HourForecastCard(
          conditionName = viewModel.currentWeatherResponse?.current?.condition?.text.toString(),
          hourForecasts = viewModel.getHourForecasts()
        )
        Spacer(modifier = Modifier.size(26.dp))
      }
      item {
        DayForecastCard(dayForecasts = viewModel.getDayForecasts())
        Spacer(modifier = Modifier.size(28.dp))
      }
      item {
        Row(
          modifier = Modifier.fillMaxWidth(),
          horizontalArrangement = Arrangement.SpaceBetween
        ) {
          PrecipitationCard(
            precipitation = "${viewModel.currentWeatherResponse?.current?.precipMm} mm"
          )
          WindCard(
            windSpeed = "${viewModel.currentWeatherResponse?.current?.windKph} kph",
            windDirection = "${viewModel.currentWeatherResponse?.current?.windDir}"
          )
        }
        Spacer(modifier = Modifier.size(12.dp))
        Row(
          modifier = Modifier.fillMaxWidth(),
          horizontalArrangement = Arrangement.SpaceBetween
        ) {
          UVCard(
            uvIndex = "${viewModel.currentWeatherResponse?.current?.uv}"
          )
          SunsetSunriseCard(
            sunsetTime = "${viewModel.forecastWeatherResponse?.forecast?.forecastday?.get(0)?.astro?.sunset}",
            sunriseTime = "${viewModel.forecastWeatherResponse?.forecast?.forecastday?.get(0)?.astro?.sunrise}"
          )
        }
        Spacer(modifier = Modifier.size(26.dp))
      }

      viewModel.forecastWeatherResponse?.alerts?.alert?.let { alerts ->
        item {
          Row {
            Icon(
              painter = painterResource(id = R.drawable.ic_notifications),
              contentDescription = null,
              tint = Color(0xFF364A7D),
              modifier = Modifier.size(14.dp)
            )
            Spacer(modifier = Modifier.size(2.dp))
            Text(
              text = "Alerts",
              style = MaterialTheme.typography.labelSmall,
              color = Color(0xFF364A7D)
            )
          }
          Spacer(modifier = Modifier.size(4.dp))
        }
        items(
          count = alerts.size,
          key = { index ->
            alerts[index].hashCode()
          }
        ) { index ->
          AlertCard(
            alertName = alerts[index].event,
            alertTitle = alerts[index].headline,
            alertTimeString = alerts[index].effective,
            alertDescription = alerts[index].desc
          )
        }
      }

    }
  }
}

@Composable
fun AppBar(
  isAdded: Boolean,
  onBackPress: () -> Unit,
  onAddRemoveButtonClick: () -> Unit
) {
  Row(
    modifier = Modifier
      .fillMaxWidth()
      .background(Color.White)
      .padding(vertical = 16.dp),
    horizontalArrangement = Arrangement.SpaceBetween,
    verticalAlignment = Alignment.CenterVertically
  ) {
    Icon(
      painter = painterResource(id = R.drawable.ic_arrow_back),
      contentDescription = null,
      modifier = Modifier
        .size(24.dp)
        .clickable { onBackPress() },
      tint = Color(0xFF303030)
    )
    AddRemoveButton(isAdded = isAdded, onTap = onAddRemoveButtonClick)
  }
}

@Composable
private fun AddRemoveButton(isAdded: Boolean, onTap: () -> Unit) {
  Row(
    modifier = Modifier.clickable { onTap() },
    verticalAlignment = Alignment.CenterVertically,
  ) {
    if (isAdded) {
      Icon(
        painter = painterResource(id = R.drawable.ic_delete),
        contentDescription = null,
        tint = Color(0xFFE34C4C),
        modifier = Modifier.size(16.dp)
      )
      Spacer(modifier = Modifier.size(6.dp))
      Text(
        text = "Remove",
        style = MaterialTheme.typography.labelMedium,
        color = Color(0xFFE34C4C)
      )
    } else {
      Icon(
        painter = painterResource(id = R.drawable.ic_add),
        contentDescription = null,
        tint = Color.Black,
        modifier = Modifier.size(16.dp)
      )
      Spacer(modifier = Modifier.size(6.dp))
      Text(text = "Add", style = MaterialTheme.typography.labelMedium, color = Color.Black)
    }
  }
}