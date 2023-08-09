package com.example.weatherapp.domain.dependency

import com.example.weatherapp.data.network.SearchService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object SearchNetworkModule {
  @Singleton
  @Provides
  fun provideSearchService(retrofit: Retrofit): SearchService =
    retrofit.create(SearchService::class.java)
}