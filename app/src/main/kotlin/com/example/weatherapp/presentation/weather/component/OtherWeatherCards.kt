package com.example.weatherapp.presentation.weather.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.weatherapp.R

@Composable
fun PrecipitationCard(
  precipitation: String,
) {
  Column(
    modifier = Modifier
      .width(174.dp)
      .height(73.dp)
      .background(color = Color(0xFFFCF9F1), shape = RoundedCornerShape(size = 8.dp))
      .padding(start = 10.dp, top = 11.dp)
  ) {
    Row(
      verticalAlignment = Alignment.CenterVertically
    ) {
      Icon(
        painter = painterResource(id = R.drawable.ic_rainy),
        contentDescription = null,
        modifier = Modifier.size(12.dp),
        tint = Color(0xFF364A7D)
      )
      Spacer(modifier = Modifier.size(2.dp))
      Text(
        text = "Precipitation",
        style = MaterialTheme.typography.labelSmall,
        color = Color(0xFF364A7D)
      )
    }
    Spacer(modifier = Modifier.size(5.dp))
    Text(
      text = precipitation,
      style = MaterialTheme.typography.labelMedium,
      color = Color(0xFF303030)
    )
  }
}

@Composable
fun WindCard(
  windSpeed: String,
  windDirection: String
) {
  Box {
    Column(
      modifier = Modifier
        .width(174.dp)
        .height(73.dp)
        .background(color = Color(0xFFFCF9F1), shape = RoundedCornerShape(size = 8.dp))
        .padding(start = 10.dp, top = 11.dp)
    ) {
      Row(
        verticalAlignment = Alignment.CenterVertically
      ) {
        Icon(
          painter = painterResource(id = R.drawable.ic_air),
          contentDescription = null,
          modifier = Modifier.size(12.dp),
          tint = Color(0xFF364A7D)
        )
        Spacer(modifier = Modifier.size(2.dp))
        Text(
          text = "Wind",
          style = MaterialTheme.typography.labelSmall,
          color = Color(0xFF364A7D)
        )
      }
      Spacer(modifier = Modifier.size(5.dp))
      Text(
        text = windSpeed,
        style = MaterialTheme.typography.labelMedium,
        color = Color(0xFF303030)
      )
    }
    Text(
      text = windDirection,
      style = MaterialTheme.typography.titleLarge,
      color = Color(0xFF303030),
      modifier = Modifier
        .align(Alignment.CenterEnd)
        .padding(end = 24.dp)
    )
  }
}

@Composable
fun UVCard(
  uvIndex: String
) {
  Column(
    modifier = Modifier
      .width(174.dp)
      .height(73.dp)
      .background(color = Color(0xFFFCF9F1), shape = RoundedCornerShape(size = 8.dp))
      .padding(start = 10.dp, top = 11.dp)
  ) {
    Row(
      verticalAlignment = Alignment.CenterVertically
    ) {
      Icon(
        painter = painterResource(id = R.drawable.ic_wb_sunny),
        contentDescription = null,
        modifier = Modifier.size(12.dp),
        tint = Color(0xFF364A7D)
      )
      Spacer(modifier = Modifier.size(2.dp))
      Text(
        text = "UV index",
        style = MaterialTheme.typography.labelSmall,
        color = Color(0xFF364A7D)
      )
    }
    Spacer(modifier = Modifier.size(5.dp))
    Text(
      text = uvIndex,
      style = MaterialTheme.typography.labelMedium,
      color = Color(0xFF303030)
    )
  }
}

@Composable
fun SunsetSunriseCard(
  sunriseTime: String,
  sunsetTime: String
) {
  Column(
    modifier = Modifier
      .width(174.dp)
      .height(73.dp)
      .background(color = Color(0xFFFCF9F1), shape = RoundedCornerShape(size = 8.dp))
      .padding(horizontal = 10.dp, vertical = 11.dp)
  ) {
    Row(
      verticalAlignment = Alignment.CenterVertically
    ) {
      Icon(
        painter = painterResource(id = R.drawable.ic_sunny),
        contentDescription = null,
        modifier = Modifier.size(12.dp),
        tint = Color(0xFF364A7D)
      )
      Spacer(modifier = Modifier.size(2.dp))
      Text(
        text = "Sun",
        style = MaterialTheme.typography.labelSmall,
        color = Color(0xFF364A7D)
      )
    }
    Spacer(modifier = Modifier.size(5.dp))
    Row(
      modifier = Modifier.fillMaxWidth(),
      horizontalArrangement = Arrangement.SpaceBetween
    ) {
      Row {
        Icon(
          painter = painterResource(id = R.drawable.ic_clear_day),
          contentDescription = null,
          modifier = Modifier.size(12.dp),
          tint = Color.Black
        )
        Spacer(modifier = Modifier.size(2.dp))
        Text(
          text = sunriseTime,
          style = MaterialTheme.typography.labelSmall,
          color = Color(0xFF303030)
        )
      }
      Row {
        Icon(
          painter = painterResource(id = R.drawable.ic_clear_night),
          contentDescription = null,
          modifier = Modifier.size(12.dp),
          tint = Color.Black
        )
        Spacer(modifier = Modifier.size(2.dp))
        Text(
          text = sunsetTime,
          style = MaterialTheme.typography.labelSmall,
          color = Color(0xFF303030)
        )
      }
    }
  }
}

@Preview
@Composable
private fun PrecipitationPreview() {
  MaterialTheme {
    PrecipitationCard(precipitation = "4 mm")
  }
}

@Preview
@Composable
private fun WindCardPreview() {
  MaterialTheme {
    WindCard(windSpeed = "25 kph", windDirection = "S")
  }
}

@Preview
@Composable
private fun UVIndexPreview() {
  MaterialTheme {
    UVCard(uvIndex = "6.0")
  }
}

@Preview
@Composable
private fun SunsetSunriseCardPreview() {
  MaterialTheme {
    SunsetSunriseCard(sunriseTime = "6:00 am", sunsetTime = "5:00 pm")
  }
}