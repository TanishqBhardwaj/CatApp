package com.example.catapp.data.repositories

import com.example.catapp.data.datasources.remote.ApiService
import com.example.catapp.data.datasources.remote.NetworkResult
import com.example.catapp.data.datasources.remote.handleApi
import com.example.catapp.data.models.ApiResponse
import com.example.catapp.utils.Constants
import javax.inject.Inject

class CatRepository @Inject constructor(private val apiService: ApiService) : ICatRepository {

    override suspend fun fetchCatData(
        pageNo: Int,
        pageLimit: Int,
        order: String
    ): NetworkResult<List<ApiResponse>> {
        return handleApi {
            apiService.fetchData(
                apiKey = Constants.CAT_API_KEY,
                page = pageNo,
                limit = pageLimit,
                order = order
            )
        }
    }
}