package com.example.myapplication.API.Inflacion

data class InflacionRequest(
    val tipo: String,              // "Tasa real", "Tasa nominal", "Valor futuro", "Valor presente"
    val tasaNominal: Double,
    val tasaReal: Double,
    val tasaInflacion: Double,
    val valor: Double,
    val periodos: Int
)
