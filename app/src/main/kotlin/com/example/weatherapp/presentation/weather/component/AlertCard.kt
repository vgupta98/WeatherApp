package com.example.weatherapp.presentation.weather.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

@Composable
fun AlertCard(
  alertName: String,
  alertTitle: String,
  alertTimeString: String,
  alertDescription: String,
) {
  Column(
    modifier = Modifier
      .fillMaxWidth()
      .background(Color(0xFFFCF9F1), shape = RoundedCornerShape(size = 8.dp))
      .padding(start = 8.dp, end = 8.dp, top = 10.dp, bottom = 16.dp)
  ) {
    Box(
      modifier = Modifier
        .background(
          color = Color(0xFFEE8B31),
          shape = RoundedCornerShape(size = 4.dp)
        )
        .padding(horizontal = 8.dp)
        .align(Alignment.End)
    ) {
      Text(
        text = alertName,
        style = MaterialTheme.typography.labelSmall,
        color = Color.White,
        modifier = Modifier.align(Alignment.Center)
      )
    }
    Spacer(modifier = Modifier.size(2.dp))
    Text(
      text = alertTitle,
      style = MaterialTheme.typography.labelMedium.copy(fontWeight = FontWeight(800)),
      color = Color.Black
    )
    Text(
      text = alertTimeString,
      style = MaterialTheme.typography.labelSmall,
      color = Color(0xFF8C8C8C)
    )
    Spacer(modifier = Modifier.size(13.dp))
    Text(text = alertDescription, style = MaterialTheme.typography.labelMedium, color = Color.Black)
  }
}

@Preview
@Composable
private fun AlertCardPreview() {
  MaterialTheme {
    AlertCard(
      alertName = "Snow",
      alertTitle = "Snow Canada",
      alertTimeString = "21 Jul, 2023 8:00 pm - 29 Jul, 2023 9:00 pm",
      alertDescription = "Conditions are favourable for the development of severe thunderstorms that may be capable of producing strong wind gusts, large hail and heavy rain and Snow."
    )
  }
}