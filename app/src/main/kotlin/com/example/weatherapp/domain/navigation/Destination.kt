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
}