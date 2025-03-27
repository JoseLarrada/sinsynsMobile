package com.example.myapplication.viewModel
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.LiveData
import com.example.myapplication.API.Anualidades.AnualidadesRequest
import com.example.myapplication.API.Anualidades.UniValorResponse
import com.example.myapplication.API.RetrofitClient
import kotlinx.coroutines.launch

class AnualidadesViewModel : ViewModel() {

    private val _uniValorResponse = MutableLiveData<UniValorResponse?>()
    val uniValorResponse: LiveData<UniValorResponse?> = _uniValorResponse

    fun calcularAnualidades(tasaAnualidad: String, periodoPago: String, anualidad: String) {
        if (tasaAnualidad.isBlank() || periodoPago.isBlank() || anualidad.isBlank()) {
            println("⚠️ Error: Todos los campos deben estar llenos")
            return
        }

        viewModelScope.launch {
            try {
                val request = AnualidadesRequest(
                    tasaAnualidad.toDouble(),
                    periodoPago.toDouble(),
                    anualidad.toDouble()
                )
                val response = RetrofitClient.apiService.calcularAnualidades(request)

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
