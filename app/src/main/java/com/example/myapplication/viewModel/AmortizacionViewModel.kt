package com.example.myapplication.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.myapplication.API.Amortizacion.AmortizacionRequest
import com.example.myapplication.API.Amortizacion.AmortizacionResponse
import com.example.myapplication.API.RetrofitClient
import kotlinx.coroutines.launch

class AmortizacionViewModel : ViewModel() {

    private val _amortizacionFrancesa = MutableLiveData<AmortizacionResponse>()
    val amortizacionFrancesa: LiveData<AmortizacionResponse> = _amortizacionFrancesa

    private val _amortizacionAlemana = MutableLiveData<AmortizacionResponse>()
    val amortizacionAlemana: LiveData<AmortizacionResponse> = _amortizacionAlemana

    private val _amortizacionAmericana = MutableLiveData<AmortizacionResponse>()
    val amortizacionAmericana: LiveData<AmortizacionResponse> = _amortizacionAmericana

    fun calcularAmortizacionFrancesa(monto: Double, tasa: Double, periodos: Int) {
        viewModelScope.launch {
            try {
                val request = AmortizacionRequest(monto, tasa, periodos)
                val response = RetrofitClient.apiService.calcularFrancesa(request)
                println(request)
                if (response.isSuccessful) {
                    println(response.body())
                    _amortizacionFrancesa.postValue(response.body())
                } else {
                    println("Error API Francesa: ${response.errorBody()?.string()}")
                }
            } catch (e: Exception) {
                println("Error de conexión Francesa: ${e.message}")
            }
        }
    }

    fun calcularAmortizacionAlemana(monto: Double, tasa: Double, periodos: Int) {
        viewModelScope.launch {
            try {
                val request = AmortizacionRequest(monto, tasa, periodos)
                val response = RetrofitClient.apiService.calcularAlemana(request)
                if (response.isSuccessful) {
                    _amortizacionAlemana.postValue(response.body())
                } else {
                    println("Error API Alemana: ${response.errorBody()?.string()}")
                }
            } catch (e: Exception) {
                println("Error de conexión Alemana: ${e.message}")
            }
        }
    }

    fun calcularAmortizacionAmericana(monto: Double, tasa: Double, periodos: Int) {
        viewModelScope.launch {
            try {
                val request = AmortizacionRequest(monto, tasa, periodos)
                val response = RetrofitClient.apiService.calcularAmericana(request)
                if (response.isSuccessful) {
                    _amortizacionAmericana.postValue(response.body())
                } else {
                    println("Error API Americana: ${response.errorBody()?.string()}")
                }
            } catch (e: Exception) {
                println("Error de conexión Americana: ${e.message}")
            }
        }
    }
}
