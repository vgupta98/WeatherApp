package com.example.weatherapp.presentation.theme

import androidx.compose.material3.Typography
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.sp
import com.example.weatherapp.R

// Set of Material typography styles to start with
val Typography = Typography(
  bodyLarge = TextStyle(
    fontSize = 24.sp,
    lineHeight = 22.sp,
    fontFamily = FontFamily(Font(R.font.new_york_large)),
    fontWeight = FontWeight(400),
  ),
  titleLarge = TextStyle(
    fontSize = 36.sp,
    fontFamily = Avenir,
    fontWeight = FontWeight(900),
    lineHeight = 28.sp,
    letterSpacing = 0.sp
  ),
  displayLarge = TextStyle(
    fontSize = 120.sp,
    fontFamily = Avenir,
    fontWeight = FontWeight(900),
    textAlign = TextAlign.Center,
  ),
  displaySmall = TextStyle(
    fontSize = 40.sp,
    lineHeight = 22.sp,
    fontFamily = Avenir,
    fontWeight = FontWeight(400),
  ),
  labelMedium = TextStyle(
    fontSize = 16.sp,
    fontFamily = Avenir,
    fontWeight = FontWeight(400),
  ),
  labelSmall = TextStyle(
    fontSize = 12.sp,
    lineHeight = 22.sp,
    fontFamily = Avenir,
    fontWeight = FontWeight(400),
  ),
  bodySmall = TextStyle(
    fontSize = 14.sp,
    lineHeight = 22.sp,
    fontFamily = Avenir,
    fontWeight = FontWeight(300),
  )
)
/* Other default text styles to override

  labelSmall = TextStyle(
      fontFamily = FontFamily.Default,
      fontWeight = FontWeight.Medium,
      fontSize = 11.sp,
      lineHeight = 16.sp,
      letterSpacing = 0.5.sp
  )
  */
