package com.example.catapp.data.datasources.remote

import com.example.catapp.data.models.ApiResponse
import com.example.catapp.utils.Constants
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
        @Query("limit") limit: Int = Constants.PAGE_SIZE,
        @Query("order") order: String = Constants.ASC
    ): Response<List<ApiResponse>>
}