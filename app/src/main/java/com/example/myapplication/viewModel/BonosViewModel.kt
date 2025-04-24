package com.example.myapplication.viewModel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.myapplication.API.Bonos.BonosRequest
import com.example.myapplication.API.Bonos.BonosResponse
import com.example.myapplication.API.RetrofitClient
import kotlinx.coroutines.launch

class BonosViewModel : ViewModel() {

    private val _bonoResponse = MutableLiveData<BonosResponse?>()
    val bonoResponse: LiveData<BonosResponse?> = _bonoResponse

    fun calcularBonos(request: BonosRequest) {
        viewModelScope.launch {
            try {
                val response = RetrofitClient.apiService.calcularBono(request)
                if (response.isSuccessful) {
                    _bonoResponse.postValue(response.body())
                } else {
                    println("Error API: ${response.errorBody()?.string()}")
                }
            } catch (e: Exception) {
                println("Error conexión: ${e.message}")
            }
        }
    }
}

