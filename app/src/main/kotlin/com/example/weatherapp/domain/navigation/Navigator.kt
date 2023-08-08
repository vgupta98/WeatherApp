@file:Suppress("BooleanPropertyNaming")

package com.example.weatherapp.domain.navigation

import com.example.weatherapp.domain.navigation.NavigationIntent.NavigateBack
import com.example.weatherapp.domain.navigation.NavigationIntent.NavigateTo
import kotlinx.coroutines.channels.BufferOverflow.DROP_LATEST
import kotlinx.coroutines.channels.Channel

class Navigator {
  val navigationChannel = Channel<NavigationIntent>(
    capacity = Int.MAX_VALUE,
    onBufferOverflow = DROP_LATEST,
  )

  suspend fun navigateTo(
    route: String,
    popUpToRoute: String? = null,
    inclusive: Boolean = false,
    isSingleTop: Boolean = false,
  ) {
    navigationChannel.send(
      NavigateTo(
        route = route,
        popUpToRoute = popUpToRoute,
        inclusive = inclusive,
        isSingleTop = isSingleTop,
      )
    )
  }

  fun tryNavigateTo(
    route: String,
    popUpToRoute: String? = null,
    inclusive: Boolean = false,
    isSingleTop: Boolean = false,
  ) {
    navigationChannel.trySend(
      NavigateTo(
        route = route,
        popUpToRoute = popUpToRoute,
        inclusive = inclusive,
        isSingleTop = isSingleTop,
      )
    )
  }

  suspend fun navigateBack(
    route: String? = null,
    inclusive: Boolean = false,
  ) {
    navigationChannel.send(
      NavigateBack(
        route = route,
        inclusive = inclusive
      )
    )
  }

  fun tryNavigateBack(
    route: String? = null,
    inclusive: Boolean = false,
  ) {
    navigationChannel.trySend(
      NavigateBack(
        route = route,
        inclusive = inclusive
      )
    )
  }
}

sealed class NavigationIntent {
  data class NavigateBack(
    val route: String? = null,
    val inclusive: Boolean = false,
  ) : NavigationIntent()

  data class NavigateTo(
    val route: String,
    val popUpToRoute: String? = null,
    val inclusive: Boolean = false,
    val isSingleTop: Boolean = false,
  ) : NavigationIntent()
}
