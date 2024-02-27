package com.example.catapp.viewmodels

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.catapp.data.datasources.remote.NetworkResult
import com.example.catapp.data.models.ApiResponse
import com.example.catapp.data.models.CatViewModelState
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

    val productList : MutableLiveData<NetworkResult<List<ApiResponse>>> = MutableLiveData()

    private val _catListState = MutableStateFlow(CatViewModelState())
    val catListState get() = _catListState.asStateFlow()

    init {
        _catListState.update {
            _catListState.value.copy(isLoading = true, isError = false)
        }
    }

    fun getProductViewModel() {
        viewModelScope.launch(Dispatchers.IO) {
            val fetchedCatData = catRepository.fetchCatData(1, 10, "ASC")
            setLoadingFalse()
            when (fetchedCatData) {
                is NetworkResult.Success -> {
                    _catListState.update {
                        _catListState.value.copy(
                            data = CatBreedMapper.mapToUiCatItems(
                                fetchedCatData.data
                            )
                        )
                    }
                }

                is NetworkResult.Error -> {
                    _catListState.update {
                        _catListState.value.copy(isError = true, errorMessage = it.errorMessage)
                    }
                }

                is NetworkResult.Exception -> {
                    _catListState.update {
                        _catListState.value.copy(isError = true, errorMessage = it.errorMessage)
                    }
                }
            }
        }
    }

    private fun setLoadingFalse() {
        _catListState.update {
            _catListState.value.copy(isLoading = false)
        }
    }
}