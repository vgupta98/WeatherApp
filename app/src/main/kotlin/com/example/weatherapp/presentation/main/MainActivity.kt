package com.example.weatherapp.presentation.main

import android.app.Activity
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.weatherapp.domain.navigation.Destination
import com.example.weatherapp.domain.navigation.NavHost
import com.example.weatherapp.domain.navigation.NavigationIntent
import com.example.weatherapp.domain.navigation.NavigationIntent.NavigateBack
import com.example.weatherapp.domain.navigation.NavigationIntent.NavigateTo
import com.example.weatherapp.domain.navigation.composable
import com.example.weatherapp.presentation.theme.WeatherAppTheme
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

  private val viewModel by viewModels<MainViewModel>()
  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContent {
      WeatherAppTheme {
        val navController = rememberNavController()
        NavigationCallBack(navHostController = navController, viewModel.navigationChannel)

        Column(Modifier.fillMaxSize()) {
          NavHost(
            navController = navController,
            startDestination = Destination.Home,
            modifier = Modifier.weight(1f),
          ) {
            composable(
              destination = Destination.Home,
            ) {
              Text(text = "Home")
            }
          }
        }
      }
    }
  }
}

@Composable
private fun NavigationCallBack(
  navHostController: NavHostController,
  navigationChannel: Channel<NavigationIntent>,
) {
  val activity = LocalContext.current as? Activity
  LaunchedEffect(Unit) {
    navigationChannel.receiveAsFlow().collect { intent ->
      if (activity?.isFinishing == true) {
        return@collect
      }
      when (intent) {
        is NavigateBack -> {
          val route = intent.route
          if (route != null) {
            navHostController.popBackStack(route, intent.inclusive)
          } else {
            navHostController.popBackStack()
          }
        }

        is NavigateTo -> navHostController.navigate(intent.route) {
          launchSingleTop = intent.isSingleTop
          intent.popUpToRoute?.let { popUpToRoute ->
            popUpTo(popUpToRoute) { inclusive = intent.inclusive }
          }
        }
      }
    }
  }
}