package com.example.catapp.data.models

sealed class CatUiState {
    data object Loading : CatUiState()
    data class Success(val data: List<CatItem>) : CatUiState()
    data class Error(val message: String) : CatUiState()
}