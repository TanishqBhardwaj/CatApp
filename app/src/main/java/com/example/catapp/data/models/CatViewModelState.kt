package com.example.catapp.data.models

data class CatViewModelState (
    val data: List<CatItem> = emptyList(),
    val isLoading: Boolean = false,
    val isError: Boolean = false,
    val errorMessage: String? = null
)