package com.example.myapplication.API.Capitalizaciones

data class CapitalizacionMixtoRequest(
    val aporteIndividual: AporteDTO,
    val aportesColectivos: CapitalizacionColectivaRequest,
    val porcentajeIndividual: Double
)
