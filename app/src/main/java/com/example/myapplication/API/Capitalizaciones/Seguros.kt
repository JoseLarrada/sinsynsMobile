package com.example.myapplication.API.Capitalizaciones

data class CapitalizacionSegurosRequest(
    val aportes: Double,
    val tasaInteres: Double,
    val anios: Int,
    val costoSeguro: Double,
    val calcularRenta: Boolean
)

data class CapitalizacionSegurosResponse(
    val capitalFinal: Double,
    val rentaPeriodica: Double
)
