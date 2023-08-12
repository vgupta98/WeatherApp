package com.example.weatherapp.domain.navigation

sealed class Destination(val route: String, vararg params: String) {
  val fullRoute: String = if (params.isEmpty()) {
    route
  } else {
    val builder = StringBuilder(route)
    params.forEach { builder.append("/{$it}") }
    builder.toString()
  }

  sealed class NoArgumentsDestination(route: String) : Destination(route) {
    operator fun invoke(): String = route
  }

  object Search : NoArgumentsDestination("search")

  object Weather: Destination("weather", "latLong") {
    const val LAT_LONG = "latLong"
    operator fun invoke(
      latLong: String,
    ): String = route.appendParams(
      LAT_LONG to latLong
    )
  }
}

internal fun String.appendParams(
  vararg params: Pair<String, Any?>,
): String {
  val builder = StringBuilder(this)

  params.forEach { pair ->
    pair.second?.run {
      builder.append("/$this")
    }
  }

  return builder.toString()
}