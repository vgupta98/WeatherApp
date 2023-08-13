package com.example.weatherapp.presentation.weather.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.example.weatherapp.R
import com.example.weatherapp.presentation.theme.WeatherAppTheme

@Composable
fun CurrentWeatherCard(
  locationName: String,
  regionName: String,
  countryName: String,
  currentTemperature: String,
  highTemperature: String,
  lowTemperature: String,
  weatherIconUrl: String,
  feelsLikeTemperature: String,
) {
  Column(
    modifier = Modifier
      .fillMaxWidth()
      .background(Color.White)
  ) {
    Text(
      text = locationName,
      color = Color.Black,
      style = MaterialTheme.typography.titleLarge.copy(fontWeight = FontWeight(800))
    )
    Text(
      text = "$regionName, $countryName",
      color = Color.Black,
      style = MaterialTheme.typography.labelMedium
    )
    Spacer(modifier = Modifier.size(32.dp))
    Row {
      Text(
        text = currentTemperature,
        style = MaterialTheme.typography.displayLarge,
        color = Color(0xFF190A36),
        modifier = Modifier.align(Alignment.Top)
      )
      Spacer(modifier = Modifier.size(11.dp))
      Text(
        text = "°c",
        style = MaterialTheme.typography.displaySmall,
        color = Color(0xFF190A36),
        modifier = Modifier
          .align(Alignment.Top)
          .weight(1f)
      )
      AsyncImage(
        model = weatherIconUrl,
        contentDescription = null,
        modifier = Modifier
          .size(64.dp)
          .align(Alignment.CenterVertically),
        contentScale = ContentScale.Crop
      )
    }
    Row {
      Icon(
        painter = painterResource(id = R.drawable.ic_arrow_upward),
        contentDescription = null,
        modifier = Modifier.size(14.dp),
        tint = Color(0xFF303030)
      )
      Text(
        text = highTemperature,
        color = Color(0xFF303030),
        style = MaterialTheme.typography.labelMedium
      )
      Spacer(modifier = Modifier.size(16.dp))
      Icon(
        painter = painterResource(id = R.drawable.ic_arrow_downward),
        contentDescription = null,
        modifier = Modifier.size(14.dp),
        tint = Color(0xFF303030)
      )
      Text(
        text = lowTemperature,
        color = Color(0xFF303030),
        style = MaterialTheme.typography.labelMedium,
        modifier = Modifier.weight(1f)
      )
      Text(
        text = "feels like $feelsLikeTemperature",
        style = MaterialTheme.typography.labelMedium.copy(fontWeight = FontWeight(300)),
        color = Color(0xFF303030),
      )
    }
  }
}

@Preview
@Composable
private fun CurrentWeatherCardPreview() {
  WeatherAppTheme {
    CurrentWeatherCard(
      locationName = "SuratGarh",
      regionName = "GangaNagar",
      countryName = "India",
      currentTemperature = "35",
      highTemperature = "40°c",
      lowTemperature = "30°c",
      weatherIconUrl = "http://cdn.weatherapi.com/weather/64x64/day/113.png",
      feelsLikeTemperature = "45°c"
    )
  }
}