package com.example.myapplication.API.Capitalizaciones

data class AporteDTO(
    val monto: Double,
    val tiempo: Double
)

data class CapitalizacionColectivaRequest(
    val aportes: List<AporteDTO>,
    val tasa: Double
)
