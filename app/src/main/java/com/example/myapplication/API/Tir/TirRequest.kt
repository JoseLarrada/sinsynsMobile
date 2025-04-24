package com.example.myapplication.API.Tir

data class TirSimpleRequest(
    val flujos: List<Double>
)

data class TirRealRequest(
    val flujos: List<Double>,
    val inflacion: Double
)

data class TirContableRequest(
    val utilidadPromedioAnual: Double,
    val inversionInicial: Double
)

data class TirModificadaRequest(
    val flujosPositivos: List<FlujoDTO>,
    val flujosNegativos: List<FlujoDTO>,
    val tasaReinversion: Double,
    val tasaFinanciamiento: Double,
    val periodos: Int
)

data class FlujoDTO(
    val monto: Double,
    val periodo: Int
)