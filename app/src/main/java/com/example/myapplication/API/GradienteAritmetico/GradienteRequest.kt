package com.example.myapplication.API.GradienteAritmetico

data class GradienteRequest(
    val primerPago: Double,
    val incrementoPago: Double,
    val tasaInteres: Double,
    val periodos: Double
)
