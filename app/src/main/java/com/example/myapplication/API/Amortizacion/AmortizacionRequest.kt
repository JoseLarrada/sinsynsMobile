package com.example.myapplication.API.Amortizacion

data class AmortizacionRequest(
    val monto: Double,
    val tasa: Double,
    val periodos: Int
)
