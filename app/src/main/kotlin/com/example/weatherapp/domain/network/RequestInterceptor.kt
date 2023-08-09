package com.example.weatherapp.domain.network

import okhttp3.Interceptor
import okhttp3.Interceptor.Chain
import okhttp3.Request
import okhttp3.Response

class RequestInterceptor : Interceptor {
  override fun intercept(chain: Chain): Response {
    val originalRequest: Request = chain.request()
    val originalUrl = originalRequest.url

    val url =
      originalUrl.newBuilder()
        .addQueryParameter("key", "2bb1677c79084effb08111442230808")
        .build()

    val newRequest = originalRequest.newBuilder()
      .url(url)
      .build()
    return chain.proceed(newRequest)
  }
}