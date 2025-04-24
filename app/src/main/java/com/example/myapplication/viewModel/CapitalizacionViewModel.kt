package com.example.myapplication.viewModel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.myapplication.API.Capitalizaciones.CapitalizacionColectivaRequest
import com.example.myapplication.API.Capitalizaciones.CapitalizacionIndividualRequest
import com.example.myapplication.API.Capitalizaciones.CapitalizacionMixtoRequest
import com.example.myapplication.API.Capitalizaciones.CapitalizacionSegurosRequest
import com.example.myapplication.API.Capitalizaciones.CapitalizacionSegurosResponse
import com.example.myapplication.API.RetrofitClient
import kotlinx.coroutines.launch

class CapitalizacionViewModel : ViewModel() {

    val resultado = MutableLiveData<Double?>()
    val resultadoSeguros = MutableLiveData<CapitalizacionSegurosResponse?>()

    fun calcularColectiva(request: CapitalizacionColectivaRequest) {
        ejecutar {
            println(request)
            val res = RetrofitClient.apiService.capitalColectiva(request)
            resultado.postValue(res.body())
            println(res.body())
        }
    }

    fun calcularIndividual(request: CapitalizacionIndividualRequest) {
        ejecutar {
            println(request)
            val res = RetrofitClient.apiService.capitalIndividual(request)
            resultado.postValue(res.body())
            println(res.body())
        }
    }

    fun calcularMixta(request: CapitalizacionMixtoRequest) {
        ejecutar {
            val res = RetrofitClient.apiService.capitalMixta(request)
            resultado.postValue(res.body())
        }
    }

    fun calcularSeguros(request: CapitalizacionSegurosRequest) {
        ejecutar {
            val res = RetrofitClient.apiService.capitalSeguros(request)
            resultadoSeguros.postValue(res.body())
        }
    }

    private fun ejecutar(block: suspend () -> Unit) {
        viewModelScope.launch {
            try {
                block()
            } catch (e: Exception) {
                println("Error: ${e.message}")
            }
        }
    }
}
