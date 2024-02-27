package com.example.catapp.data.datasources.remote

import com.example.catapp.data.models.ApiResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Headers
import retrofit2.http.Query

interface ApiService {

    @Headers("Content-Type: application/json")
    @GET("breeds")
    suspend fun fetchData(
        @Header("x-api-key") apiKey: String,
        @Query("page") page: Int = 1,
        @Query("limit") limit: Int = 10,
        @Query("order") order: String = "ASC"
    ): Response<List<ApiResponse>>
}