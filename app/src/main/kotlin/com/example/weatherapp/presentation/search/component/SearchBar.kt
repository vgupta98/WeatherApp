package com.example.weatherapp.presentation.search.component

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.weatherapp.R
import com.example.weatherapp.presentation.theme.WeatherAppTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SearchBar(
  searchValue: String,
  onSearchValueChange: (String) -> Unit
) {
  val focusRequester = remember {
    FocusRequester()
  }
  var isFocused by remember {
    mutableStateOf(false)
  }

  Row(
    modifier = Modifier
      .fillMaxWidth()
      .padding(horizontal = 18.dp)
      .background(color = Color(0xFFF4F4F4), shape = RoundedCornerShape(size = 8.dp)),
    verticalAlignment = Alignment.CenterVertically,
    horizontalArrangement = Arrangement.Center
  ) {
    Spacer(modifier = Modifier.size(11.dp))
    Icon(
      painter = painterResource(id = R.drawable.ic_search),
      contentDescription = null,
      tint = Color(0xFFBEBEBE)
    )
    Box(
      modifier = Modifier.weight(1f)
    ) {
      TextField(
        value = searchValue,
        onValueChange = onSearchValueChange,
        modifier = Modifier
          .heightIn(39.dp)
          .focusRequester(focusRequester)
          .onFocusChanged { isFocused = it.isFocused },
        placeholder = {
          Text(
            text = "City, Region or US/UK zip code",
            style = MaterialTheme.typography.labelMedium,
            color = Color(0xFFBEBEBE),
          )
        },
        textStyle = MaterialTheme.typography.labelMedium,
        singleLine = true,
        colors = TextFieldDefaults.textFieldColors(
          containerColor = Color.Transparent,
          textColor = Color(0xFF303030),
          cursorColor = Color(0xFF303030),
          unfocusedIndicatorColor = Color.Transparent,
          focusedIndicatorColor = Color.Transparent
        ),
        shape = RoundedCornerShape(8.dp),
      )
      if (searchValue.isNotEmpty()) {
        Icon(
          modifier = Modifier
            .align(Alignment.CenterEnd)
            .clickable { onSearchValueChange("") },
          painter = painterResource(id = R.drawable.ic_close),
          contentDescription = null,
          tint = Color(0xFFBEBEBE)
        )
      }
    }
    Spacer(modifier = Modifier.size(16.dp))
  }
}

@Preview
@Composable
private fun SearchBarPreview() {
  var searchValue by remember {
    mutableStateOf("")
  }
  WeatherAppTheme {

    SearchBar(
      searchValue = searchValue,
      onSearchValueChange = { searchValue = it },
    )

  }
}
