package com.example.myapplication.API.Capitalizaciones

data class CapitalizacionIndividualRequest(
    val aporte: Double,
    val tasa: Double,
    val numAnios: Int,
    val capitalizaciones: Int
)
