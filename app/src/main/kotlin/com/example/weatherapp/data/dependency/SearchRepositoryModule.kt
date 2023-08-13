package com.example.weatherapp.data.dependency

import com.example.weatherapp.data.localdatabase.dao.LocationDao
import com.example.weatherapp.data.network.SearchService
import com.example.weatherapp.data.repository.SearchRepositoryImpl
import com.example.weatherapp.domain.repository.SearchRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.scopes.ViewModelScoped

@Module
@InstallIn(ViewModelComponent::class)
object SearchRepositoryModule {

  @ViewModelScoped
  @Provides
  fun provideSearchRepository(
    searchService: SearchService,
    locationDao: LocationDao
  ): SearchRepository = SearchRepositoryImpl(
    searchService, locationDao
  )
}