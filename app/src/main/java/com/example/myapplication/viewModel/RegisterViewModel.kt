package com.example.myapplication.viewModel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.myapplication.API.ApiService
import com.example.myapplication.API.Registro.UserRegisterRequestDTO
import com.example.myapplication.API.Registro.UserRegisterResponse
import com.example.myapplication.API.RetrofitClient
import kotlinx.coroutines.launch

class RegisterViewModel : ViewModel() {

    var name by mutableStateOf("")
    var lastname by mutableStateOf("")
    var password by mutableStateOf("")
    var cellPhone by mutableStateOf("")
    var identification by mutableStateOf("")

    var isLoading by mutableStateOf(false)
    var errorMessage by mutableStateOf<String?>(null)

    // Variable para almacenar el JWT retornado por el backend
    var jwtResponse by mutableStateOf<UserRegisterResponse?>(null)

    fun registerUser() {
        val request = UserRegisterRequestDTO(
            name = name,
            lastname = lastname,
            password = password,
            cellPhone = cellPhone,
            identification = identification
        )

        viewModelScope.launch {
            isLoading = true
            try {
                val response = RetrofitClient.apiService.registrarse(request)
                if (response.isSuccessful) {
                    // Aquí ya obtenemos un String, no un JSON
                    jwtResponse = response.body()!!
                    errorMessage = null
                } else {
                    // Si hay un error, muestra el mensaje de error
                    errorMessage = "Error: ${response.message()}"
                }
            } catch (e: Exception) {
                errorMessage = "Error al registrar: ${e.message}"
            } finally {
                isLoading = false
            }
        }
    }
}
