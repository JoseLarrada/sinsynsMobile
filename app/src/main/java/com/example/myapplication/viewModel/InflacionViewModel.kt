package com.example.myapplication.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.myapplication.API.Inflacion.InflacionRequest
import com.example.myapplication.API.RetrofitClient
import kotlinx.coroutines.launch

class InflacionViewModel : ViewModel() {

    private val _resultado = MutableLiveData<Double?>()
    val resultado: LiveData<Double?> = _resultado

    fun calcularInflacion(request: InflacionRequest) {
        viewModelScope.launch {
            try {
                val response = RetrofitClient.apiService.calcularInflacion(request)
                if (response.isSuccessful) _resultado.postValue(response.body())
                else println("Error API: ${response.errorBody()?.string()}")
            } catch (e: Exception) {
                println("Error conexión: ${e.message}")
            }
        }
    }
}
