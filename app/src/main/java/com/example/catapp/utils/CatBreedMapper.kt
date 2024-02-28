package com.example.catapp.utils

import com.example.catapp.data.models.ApiResponse
import com.example.catapp.data.models.CatItem

object CatBreedMapper {

    fun mapToUiCatItems(apiResponses: List<ApiResponse>): List<CatItem> {
        return apiResponses.map { apiResponse ->
            mapApiResponseToUiCatData(apiResponse)
        }
    }

    private fun mapApiResponseToUiCatData(apiResponse: ApiResponse): CatItem {
        return CatItem(
            apiResponse.id,
            apiResponse.name,
            apiResponse.image?.url,
            apiResponse.origin,
            apiResponse.temperament,
            apiResponse.lifeSpan,
            apiResponse.description
        )
    }
}