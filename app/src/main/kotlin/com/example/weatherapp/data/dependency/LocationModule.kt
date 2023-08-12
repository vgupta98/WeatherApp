package com.example.weatherapp.data.dependency

import android.content.Context
import com.example.weatherapp.data.location.DefaultLocationClient
import com.example.weatherapp.domain.location.LocationClient
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityComponent
import dagger.hilt.android.qualifiers.ActivityContext
import dagger.hilt.android.qualifiers.ApplicationContext

@Module
@InstallIn(ActivityComponent::class)
object LocationModule {

  @Provides
  fun provideLocationClient(
    @ActivityContext context: Context,
    client: FusedLocationProviderClient,
  ): LocationClient = DefaultLocationClient(
    context, client
  )

  @Provides
  fun provideFusedLocationProvideClient(
    @ApplicationContext context: Context
  ): FusedLocationProviderClient =
    LocationServices.getFusedLocationProviderClient(context)
}