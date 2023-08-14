package com.example.weatherapp.presentation.weather.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.example.weatherapp.domain.model.HourForecast
import com.example.weatherapp.presentation.weather.getCurrentHour

@Composable
fun HourForecastCard(
  conditionName: String,
  hourForecasts: List<HourForecastUiModel>
) {
  Column(
    modifier = Modifier
      .fillMaxWidth()
      .background(Color(0xFFF5F5F5), shape = RoundedCornerShape(size = 8.dp))
      .padding(horizontal = 12.dp, vertical = 11.dp)
  ) {
    Text(text = conditionName, style = MaterialTheme.typography.labelMedium, color = Color.Black)
    Spacer(modifier = Modifier.size(8.dp))
    Spacer(
      modifier = Modifier
        .height(1.dp)
        .fillMaxWidth()
        .background(Color(0xFFEFEFEF))
    )
    Spacer(modifier = Modifier.size(8.dp))
    Row(
      modifier = Modifier.fillMaxWidth(),
      verticalAlignment = Alignment.CenterVertically,
      horizontalArrangement = Arrangement.SpaceBetween
    ) {
      hourForecasts.forEach { forecast ->
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
          Text(
            text = forecast.hour,
            style = MaterialTheme.typography.labelSmall,
            color = Color.Black
          )
          AsyncImage(
            model = forecast.icon,
            contentDescription = null,
            modifier = Modifier.size(16.dp)
          )
          Text(
            text = forecast.temperature,
            style = MaterialTheme.typography.labelSmall,
            color = Color.Black
          )
        }
      }
    }
  }
}

data class HourForecastUiModel(
  val hour: String,
  val icon: String,
  val temperature: String
)

fun HourForecast.toHourForecastUiModel(): HourForecastUiModel = HourForecastUiModel(
  hour = timeEpoch.getCurrentHour().toString(),
  icon = "http:${condition.icon}",
  temperature = tempC.toString()
)

@Preview
@Composable
private fun HourForecastCardPreview() {
  MaterialTheme {
    HourForecastCard(
      conditionName = "Sunny",
      hourForecasts = listOf(
        HourForecastUiModel(
          hour = "Now",
          icon = "http://cdn.weatherapi.com/weather/64x64/day/113.png",
          temperature = "19°c"
        ),
        HourForecastUiModel(
          hour = "2",
          icon = "http://cdn.weatherapi.com/weather/64x64/day/113.png",
          temperature = "19°c"
        ),
        HourForecastUiModel(
          hour = "3",
          icon = "http://cdn.weatherapi.com/weather/64x64/day/113.png",
          temperature = "19°c"
        ),
        HourForecastUiModel(
          hour = "4",
          icon = "http://cdn.weatherapi.com/weather/64x64/day/113.png",
          temperature = "19°c"
        ),
        HourForecastUiModel(
          hour = "5",
          icon = "http://cdn.weatherapi.com/weather/64x64/day/113.png",
          temperature = "19°c"
        ),
        HourForecastUiModel(
          hour = "6",
          icon = "http://cdn.weatherapi.com/weather/64x64/day/113.png",
          temperature = "19°c"
        ),
        HourForecastUiModel(
          hour = "7",
          icon = "http://cdn.weatherapi.com/weather/64x64/day/113.png",
          temperature = "19°c"
        )
      )
    )
  }
}