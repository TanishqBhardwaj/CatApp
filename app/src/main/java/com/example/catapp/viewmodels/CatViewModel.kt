package com.example.catapp.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.catapp.data.datasources.remote.NetworkResult
import com.example.catapp.data.models.ApiResponse
import com.example.catapp.data.models.CatUiState
import com.example.catapp.data.repositories.ICatRepository
import com.example.catapp.utils.CatBreedMapper
import com.example.catapp.utils.Constants
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CatViewModel @Inject constructor(private val catRepository: ICatRepository) : ViewModel() {

    private val _catListState: MutableStateFlow<CatUiState> = MutableStateFlow(CatUiState.Loading)
    val catListState get() = _catListState.asStateFlow()

    var pageNumber = 1
    private var catResponseList = mutableListOf<ApiResponse>()

    fun getCatData() {
        viewModelScope.launch(Dispatchers.IO) {
            val fetchedCatData = catRepository.fetchCatData(pageNumber, Constants.PAGE_SIZE, Constants.ASC)
            when (fetchedCatData) {
                is NetworkResult.Success -> {
                    pageNumber++
                    if (catResponseList.isEmpty()) {
                        catResponseList = fetchedCatData.data.toMutableList()
                    } else {
                        catResponseList.addAll(fetchedCatData.data)
                    }
                    _catListState.update {
                        CatUiState.Success(CatBreedMapper.mapToUiCatItems(
                            catResponseList
                        ))
                    }
                }

                is NetworkResult.Error -> {
                    _catListState.update {
                        CatUiState.Error("Error")
                    }
                }

                is NetworkResult.Exception -> {
                    _catListState.update {
                        CatUiState.Error("Error")
                    }
                }
            }
        }
    }
}