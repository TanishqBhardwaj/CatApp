package com.example.catapp.data.datasources.remote

import com.example.catapp.data.models.ApiResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Headers

interface ApiService {

    @Headers("Content-Type: application/json")
    @GET("breeds")
    suspend fun fetchData(
        @Header("x-api-key") apiKey: String,
    ): Response<List<ApiResponse>>
}