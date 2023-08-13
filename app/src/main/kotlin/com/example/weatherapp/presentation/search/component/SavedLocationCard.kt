package com.example.weatherapp.presentation.search.component

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.Orientation
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.FractionalThreshold
import androidx.compose.material.rememberSwipeableState
import androidx.compose.material.swipeable
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.example.weatherapp.presentation.theme.Avenir
import com.example.weatherapp.presentation.theme.WeatherAppTheme
import kotlin.math.roundToInt

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun SavedLocationCard(
  locationName: String,
  locationRegion: String,
  temperature: String,
  weatherIconUrl: String,
  weatherTime: String,
  onTap: () -> Unit,
  onDelete: () -> Unit
) {

  val swipeState = rememberSwipeableState(
    initialValue = 0,
  )
  val screenWidth = LocalConfiguration.current.screenWidthDp.dp
  val sizePx = with(LocalDensity.current) { screenWidth.toPx() }
  val anchors = mapOf(0f to 0, -sizePx to 1) // Maps anchor points (in px) to states

  LaunchedEffect(swipeState.currentValue) {
    if (swipeState.currentValue == 1) {
      onDelete()
    }
  }

  Box {
    Box(
      modifier = Modifier
        .matchParentSize()
        .background(Color.Red)
    ) {
      Text(
        text = "Delete",
        color = Color.White,
        style = MaterialTheme.typography.labelMedium,
        modifier = Modifier
          .align(Alignment.CenterEnd)
          .padding(end = 21.dp)
      )
    }
    Box(
      Modifier
        .background(Color.Transparent)
        .offset { IntOffset(swipeState.offset.value.roundToInt(), 0) }
        .swipeable(
          state = swipeState,
          anchors = anchors,
          thresholds = { _, _ -> FractionalThreshold(0.8f) },
          orientation = Orientation.Horizontal
        )
    ) {
      Column(
        modifier = Modifier
          .background(Color.White)
          .fillMaxWidth()
          .clickable { onTap() }
          .padding(start = 16.dp, end = 16.dp, bottom = 15.dp, top = 35.dp)
      ) {
        Row(
          verticalAlignment = Alignment.Top
        ) {
          Text(
            text = locationName,
            modifier = Modifier.weight(1f),
            style = MaterialTheme.typography.bodyLarge,
            color = Color.Black
          )
          Text(
            text = temperature,
            style = MaterialTheme.typography.bodyLarge.copy(fontFamily = Avenir),
            color = Color.Black
          )
          Spacer(modifier = Modifier.size(8.dp))
          AsyncImage(
            model = weatherIconUrl,
            contentDescription = null,
            modifier = Modifier.size(22.dp),
            contentScale = ContentScale.Crop
          )
        }
        Spacer(modifier = Modifier.size(8.dp))
        Row {
          Text(
            text = locationRegion,
            modifier = Modifier.weight(1f),
            style = MaterialTheme.typography.bodySmall,
            color = Color.Black
          )
          Text(
            text = weatherTime,
            style = MaterialTheme.typography.bodySmall.copy(fontWeight = FontWeight(400)),
            color = Color(0xFFA1A1A1)
          )
        }
      }
      Spacer(
        modifier = Modifier
          .align(Alignment.BottomCenter)
          .fillMaxWidth()
          .height(1.dp)
          .padding(start = 16.dp)
          .background(Color(0xFFEFEFEF))
      )
    }
  }
}

@Preview
@Composable
private fun SavedLocationCardPreview() {
  WeatherAppTheme {
    Box(modifier = Modifier.background(Color.Black)) {
      SavedLocationCard(
        locationName = "SuratGarh",
        locationRegion = "Ganganagar, Rajasthan",
        temperature = "35°C",
        weatherIconUrl = "http://cdn.weatherapi.com/weather/64x64/day/113.png",
        weatherTime = "1:00 PM",
        onTap = { }
      ) {}
    }
  }
}