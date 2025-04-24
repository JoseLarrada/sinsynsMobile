package com.example.myapplication.API.GradienteGeometrico

data class GradienteGRequest(
    val primerPago: Double,
    val tasaInteres: Double,
    val tasaCrecimiento: Double,
    val periodos: Int
)