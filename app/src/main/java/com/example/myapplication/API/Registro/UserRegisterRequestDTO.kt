package com.example.myapplication.API.Registro

data class UserRegisterRequestDTO(
    val name: String,
    val lastname: String,
    val password: String,
    val cellPhone: String,
    val identification: String
)
