package com.example.myapplication.API.Bonos

data class BonosRequest(
    val valorNominal: Double,
    val tasaCupon: Double,
    val tasaMercado: Double,
    val años: Int,
    val frecuencia: String,
    val precioMercado: Double
)