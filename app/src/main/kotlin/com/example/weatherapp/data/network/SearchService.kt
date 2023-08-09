package com.example.weatherapp.data.network

import com.example.weatherapp.data.model.SearchResult
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface SearchService {
  @GET("/v1/search.json")
  suspend fun search(
    @Query("q") query: String
  ): Response<List<SearchResult>>
}