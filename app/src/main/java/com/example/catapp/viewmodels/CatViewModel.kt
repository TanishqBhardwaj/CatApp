package com.example.catapp.viewmodels

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.catapp.data.datasources.remote.NetworkResult
import com.example.catapp.data.models.ApiResponse
import com.example.catapp.data.repositories.ICatRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CatViewModel @Inject constructor(private val catRepository: ICatRepository) : ViewModel() {

    val productList : MutableLiveData<NetworkResult<List<ApiResponse>>> = MutableLiveData()

    fun getProductViewModel() {
        viewModelScope.launch {
            productList.value = catRepository.fetchCatData(1, 10, "ASC")
        }
    }
}