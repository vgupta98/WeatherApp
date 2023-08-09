package com.example.weatherapp.domain.extension

import retrofit2.HttpException
import retrofit2.Response

fun <T> Response<T>.parse(): T {
  if (isSuccessful) {
    return body() ?: body() as T
  } else {
    throw HttpException(this)
  }
}