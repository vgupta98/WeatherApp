package com.example.weatherapp.data.dependency

import com.example.weatherapp.domain.network.RequestInterceptor
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {

  @Provides
  @Singleton
  fun provideOkhttpClient(
    requestInterceptor: RequestInterceptor
  ): OkHttpClient {
    val client = OkHttpClient.Builder()
    client.addInterceptor(requestInterceptor)
    val logging = HttpLoggingInterceptor()
    logging.setLevel(HttpLoggingInterceptor.Level.BODY)
    client.addInterceptor(logging)
    return client.build()
  }

  @Provides
  @Singleton
  fun provideRetrofit(
    okHttpClient: OkHttpClient,
    moshi: Moshi,
  ): Retrofit = Retrofit.Builder()
    .baseUrl("http://api.weatherapi.com/")
    .client(okHttpClient)
    .addConverterFactory(MoshiConverterFactory.create(moshi).asLenient())
    .build()

  @Singleton
  @Provides
  fun provideMoshi(): Moshi =
    Moshi.Builder().add(KotlinJsonAdapterFactory()).build()

  @Singleton
  @Provides
  fun provideInterceptor(): RequestInterceptor = RequestInterceptor()
}