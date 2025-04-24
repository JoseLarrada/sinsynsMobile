package com.example.myapplication.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.myapplication.API.GradienteGeometrico.GradienteGRequest
import com.example.myapplication.API.RetrofitClient
import kotlinx.coroutines.launch

class GradienteGeometricoViewModel : ViewModel() {

    private val _vp = MutableLiveData<Double?>()
    val valorPresente: LiveData<Double?> = _vp

    private val _vf = MutableLiveData<Double?>()
    val valorFuturo: LiveData<Double?> = _vf

    fun calcularVP(request: GradienteGRequest) {
        viewModelScope.launch {
            try {
                println(request)
                val response = RetrofitClient.apiService.calcularValorPresente(request)
                if (response.isSuccessful) _vp.postValue(response.body())
                else println("Error API: ${response.errorBody()?.string()}")
            } catch (e: Exception) {
                println("Error conexión VP: ${e.message}")
            }
        }
    }

    fun calcularVF(request: GradienteGRequest) {
        viewModelScope.launch {
            try {
                val response = RetrofitClient.apiService.calcularValorFuturo(request)
                if (response.isSuccessful) _vf.postValue(response.body())
                else println("Error API: ${response.errorBody()?.string()}")
            } catch (e: Exception) {
                println("Error conexión VF: ${e.message}")
            }
        }
    }
}
