package com.example.weatherapp.data.dependency

import android.content.Context
import androidx.room.Room
import com.example.weatherapp.data.localdatabase.LocalDatabase
import com.example.weatherapp.data.localdatabase.dao.LocationDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

  @Singleton
  @Provides
  fun provideDb(
    @ApplicationContext context: Context,
  ): LocalDatabase = Room
    .databaseBuilder(
      context,
      LocalDatabase::class.java,
      LocalDatabase.DATABASE_NAME
    )
    .fallbackToDestructiveMigration()
    .build()

  @Singleton
  @Provides
  fun provideLocationDao(db: LocalDatabase): LocationDao = db.locationDao()
}