package com.beykocbtcapp.hicat.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.beykocbtcapp.hicat.model.Cat
import com.beykocbtcapp.hicat.repository.ApiResponse
import com.beykocbtcapp.hicat.repository.CatRepository
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.launch

class CatViewModel: ViewModel() {
    private val userRepository = CatRepository()

    private val _catLiveData = MutableLiveData<ApiResponse<List<Cat?>?>>()
    val catLiveData: LiveData<ApiResponse<List<Cat?>?>> get() = _catLiveData

    fun getCat() {
        viewModelScope.launch {
            userRepository.geCat().collect { data ->
                _catLiveData.value = data
            }
        }
    }


}