package com.example.catapp.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.catapp.data.datasources.remote.NetworkResult
import com.example.catapp.data.models.CatUiState
import com.example.catapp.data.repositories.ICatRepository
import com.example.catapp.utils.CatBreedMapper
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

    fun getCatData() {
        _catListState.update {
            CatUiState.Loading
        }
        viewModelScope.launch(Dispatchers.IO) {
            val fetchedCatData = catRepository.fetchCatData(1, 10, "ASC")
            when (fetchedCatData) {
                is NetworkResult.Success -> {
                    _catListState.update {
                        CatUiState.Success(CatBreedMapper.mapToUiCatItems(
                            fetchedCatData.data
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