package com.example.weatherapp.data.localdatabase

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.weatherapp.data.localdatabase.dao.LocationDao
import com.example.weatherapp.domain.model.Location

@Database(
  entities = [
    Location::class
  ],
  version = 1
)
abstract class LocalDatabase: RoomDatabase() {

  abstract fun locationDao(): LocationDao

  companion object {
    const val DATABASE_NAME = "Weather_db"
  }
}