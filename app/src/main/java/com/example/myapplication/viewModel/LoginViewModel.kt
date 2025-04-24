package com.example.myapplication.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.myapplication.API.Login.LoginRequest
import com.example.myapplication.API.Login.LoginResponse
import com.example.myapplication.API.RetrofitClient
import kotlinx.coroutines.launch

class LoginViewModel : ViewModel() {
    private val _loginResponse = MutableLiveData<LoginResponse?>()
    val loginResponse: LiveData<LoginResponse?> = _loginResponse

    fun realizarLogin(identificacion: String, contraseña: String) {
        viewModelScope.launch {
            try {
                val request = LoginRequest(
                    identification = identificacion,
                    password = contraseña
                )
                println(request)
                val response = RetrofitClient.apiService.iniciarSesion(request)
                println(response)
                if (response.isSuccessful) {
                    _loginResponse.postValue(response.body())
                    println(response.body())
                } else {
                    println("Error en la API (Login): ${response.errorBody()?.string()}")
                }
            } catch (e: Exception) {
                println("Error al llamar a la API (Login): ${e.message}")
            }
        }
    }
}
