package com.example.myapplication.API.Bonos

data class BonosResponse(
    val precioBono: Double,
    val rendimientoYTM: Double,
    val duracion: Double,
    val convexidad: Double
)