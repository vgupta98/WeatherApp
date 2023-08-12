package com.example.weatherapp.data.localdatabase.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.weatherapp.domain.model.Location

@Dao
interface LocationDao {

  @Insert(onConflict = OnConflictStrategy.REPLACE)
  suspend fun upsert(location: Location): Long

  @Query("SELECT * from Location")
  suspend fun getAllLocations(): List<Location>

  @Query("DELETE from Location WHERE id = :id")
  suspend fun deleteLocation(id: Long)

  @Query("SELECT * from Location WHERE name = :name AND region = :region AND lat = :lat AND lon = :lon")
  suspend fun getLocationByParams(name: String, region: String, lat: Double, lon: Double): List<Location>
}