package com.example.myapplication.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.myapplication.API.Anualidades.UniValorResponse
import com.example.myapplication.API.GradienteAritmetico.GradienteRequest
import com.example.myapplication.API.RetrofitClient
import kotlinx.coroutines.launch

class GradientesViewModel : ViewModel() {
    private val _uniValorResponse = MutableLiveData<UniValorResponse?>()
    val uniValorResponse: LiveData<UniValorResponse?> = _uniValorResponse

    fun calcularGradienteFuturo(primerPago: Double, incrementoPago: Double, tasaInteres: Double, periodos: Double) {
        viewModelScope.launch {
            try {
                val request = GradienteRequest(primerPago, incrementoPago, tasaInteres, periodos)
                val response = RetrofitClient.apiService.calcularGradienteFuturo(request)

                if (response.isSuccessful) {
                    _uniValorResponse.postValue(response.body())
                } else {
                    println("Error API: ${response.errorBody()?.string()}")
                }
            } catch (e: Exception) {
                println("Error al conectar con la API: ${e.message}")
            }
        }
    }
    fun calcularGradientePresente(primerPago: Double, incrementoPago: Double, tasaInteres: Double, periodos: Double) {
        viewModelScope.launch {
            try {
                val request = GradienteRequest(primerPago, incrementoPago, tasaInteres, periodos)
                val response = RetrofitClient.apiService.calcularGradientePresente(request)

                if (response.isSuccessful) {
                    _uniValorResponse.postValue(response.body())
                } else {
                    println("Error API: ${response.errorBody()?.string()}")
                }
            } catch (e: Exception) {
                println("Error al conectar con la API: ${e.message}")
            }
        }
    }
}