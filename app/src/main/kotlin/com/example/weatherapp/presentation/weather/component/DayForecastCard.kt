package com.example.weatherapp.presentation.weather.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.example.weatherapp.R

@Composable
fun DayForecastCard(
  dayForecasts: List<DayForecast>
) {
  Column(
    modifier = Modifier
      .fillMaxWidth()
      .background(color = Color(0xFFF5F5F5), shape = RoundedCornerShape(size = 8.dp))
      .padding(10.dp)
  ) {
    Row {
      Icon(
        painter = painterResource(id = R.drawable.ic_calendar_month),
        contentDescription = null,
        modifier = Modifier.size(14.dp),
        tint = Color(0xFF364A7D)
      )
      Spacer(modifier = Modifier.size(4.dp))
      Text(
        text = "5-day forecast",
        style = MaterialTheme.typography.labelSmall.copy(fontWeight = FontWeight(800)),
        color = Color(0xFF364A7D)
      )
    }
    Spacer(modifier = Modifier.size(6.dp))
    dayForecasts.forEach { forecast ->
      DayForecastRow(forecast = forecast)
    }
  }
}

@Composable
private fun DayForecastRow(forecast: DayForecast) {
  Column {
    Row(
      modifier = Modifier.padding(vertical = 4.dp)
    ) {
      Text(
        text = forecast.day,
        modifier = Modifier.weight(1f),
        style = MaterialTheme.typography.labelSmall,
        color = Color(0xFF303030)
      )
      AsyncImage(
        model = forecast.icon,
        contentDescription = null,
        modifier = Modifier.size(24.dp)
      )
      Spacer(modifier = Modifier.size(16.dp))
      Icon(
        painter = painterResource(id = R.drawable.ic_arrow_upward),
        contentDescription = null,
        modifier = Modifier.size(14.dp),
        tint = Color(0xFF303030)
      )
      Text(
        text = forecast.highTemperature,
        style = MaterialTheme.typography.bodySmall.copy(fontWeight = FontWeight(400)),
        color = Color(0xFF303030)
      )
      Spacer(modifier = Modifier.size(16.dp))
      Icon(
        painter = painterResource(id = R.drawable.ic_arrow_downward),
        contentDescription = null,
        modifier = Modifier.size(14.dp),
        tint = Color(0xFF303030)
      )
      Text(
        text = forecast.lowTemperature,
        style = MaterialTheme.typography.bodySmall.copy(fontWeight = FontWeight(400)),
        color = Color(0xFF303030)
      )
    }
    Spacer(
      modifier = Modifier
        .height(1.dp)
        .fillMaxWidth()
        .background(Color(0xFFEFEFEF))
    )
  }
}

data class DayForecast(
  val day: String,
  val icon: String,
  val lowTemperature: String,
  val highTemperature: String
)

@Preview
@Composable
private fun DayForecastCardPreview() {
  MaterialTheme {
    DayForecastCard(
      dayForecasts = listOf(
        DayForecast(
          day = "Today",
          icon = "http://cdn.weatherapi.com/weather/64x64/day/113.png",
          lowTemperature = "19°c",
          highTemperature = "39°c"
        ),
        DayForecast(
          day = "Mon",
          icon = "http://cdn.weatherapi.com/weather/64x64/day/113.png",
          lowTemperature = "19°c",
          highTemperature = "39°c"
        ),
        DayForecast(
          day = "Tues",
          icon = "http://cdn.weatherapi.com/weather/64x64/day/113.png",
          lowTemperature = "19°c",
          highTemperature = "39°c"
        ),
        DayForecast(
          day = "Tues",
          icon = "http://cdn.weatherapi.com/weather/64x64/day/113.png",
          lowTemperature = "19°c",
          highTemperature = "39°c"
        ),
        DayForecast(
          day = "Tues",
          icon = "http://cdn.weatherapi.com/weather/64x64/day/113.png",
          lowTemperature = "19°c",
          highTemperature = "39°c"
        ),

        )
    )
  }
}