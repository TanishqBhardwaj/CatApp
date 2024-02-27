package com.example.catapp.data.repositories

import com.example.catapp.data.datasources.remote.NetworkResult
import com.example.catapp.data.models.ApiResponse

interface ICatRepository {

    suspend fun fetchCatData(
        pageNo: Int,
        order: String,
        pageLimit: Int
    ): NetworkResult<List<ApiResponse>>
}