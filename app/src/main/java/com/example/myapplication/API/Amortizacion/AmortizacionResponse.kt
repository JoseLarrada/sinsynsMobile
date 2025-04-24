package com.example.myapplication.API.Amortizacion

data class AmortizacionResponse(
    val tabla: List<AmortizacionFila>
)

data class AmortizacionFila(
    val periodo: Int,
    val cuota: Double,
    val interes: Double,
    val amortizacion: Double,
    val saldo: Double
)
