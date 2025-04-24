package com.example.myapplication.Screens

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import java.text.SimpleDateFormat
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.util.Date
import java.util.Locale
import kotlin.math.pow

// Data class para el formulario de préstamos
data class PrestamoDto(
    val montoPrestamo: Double = 0.0,
    val cuotas: Int = 0,
    val tipoInteres: String = "SIMPLE",
    val periodicidad: Int = 1,
    val metodoCalculo: String = "NORMAL",
    val tasa: Double = 0.0
)

// Data class para el resultado del cálculo
data class ResultadoCalculo(
    val interes: Double = 0.0,
    val total: Double = 0.0,
    val cuotaMensual: Double = 0.0,
    val detalles: String = "",
    val cuotas: List<CuotaDetalle> = emptyList()
)

// Data class para detalles de cuota (usado en algunos métodos)
data class CuotaDetalle(
    val numeroCuota: Int,
    val monto: Double,
    val amortizacion: Double = 0.0,
    val interes: Double = 0.0
)

// Data class para un préstamo guardado
data class Prestamo(
    val id: Long = System.currentTimeMillis(),
    val dto: PrestamoDto,
    val resultado: ResultadoCalculo,
    val fecha: String = SimpleDateFormat("dd/MM/yyyy HH:mm", Locale.getDefault()).format(Date())
)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PrestamosScreen() {
    // Estado para lista de préstamos guardados
    val prestamos = remember { mutableStateListOf<Prestamo>() }

    // Estado para el formulario
    var formData by remember { mutableStateOf(PrestamoDto()) }

    // Estado para mostrar resultados
    var resultado by remember { mutableStateOf<ResultadoCalculo?>(null) }

    val tiposInteres = listOf("SIMPLE", "COMPUESTO", "FRANCES", "ALEMAN",
        "GRADIENTE ARITMETICO", "GRADIENTE GEOMETRICO")

    val periodicidades = listOf(
        Pair(1, "Mensual"),
        Pair(2, "Bimestral"),
        Pair(3, "Trimestral"),
        Pair(4, "Cuatrimestral"),
        Pair(6, "Semestral"),
        Pair(12, "Anual")
    )

    val metodosCalculo = listOf("NORMAL", "EXACTO", "COMERCIAL")

    // Función para calcular el préstamo según el tipo seleccionado
    fun calcularPrestamo(): ResultadoCalculo {
        return when (formData.tipoInteres) {
            "SIMPLE" -> calcularSimple(formData)
            "COMPUESTO" -> calcularCompuesto(formData)
            "FRANCES" -> calcularFrances(formData)
            "ALEMAN" -> calcularAleman(formData)
            "GRADIENTE ARITMETICO" -> calcularGradienteAritmetico(formData)
            "GRADIENTE GEOMETRICO" -> calcularGradienteGeometrico(formData)
            else -> ResultadoCalculo(detalles = "Tipo de interés no válido")
        }
    }

    // Función para guardar el préstamo
    @RequiresApi(Build.VERSION_CODES.O)
    fun guardarPrestamo() {
        val resultadoCalculo = calcularPrestamo()
        val nuevoPrestamo = Prestamo(
            dto = formData,
            resultado = resultadoCalculo
        )

        prestamos.add(nuevoPrestamo)
        formData = PrestamoDto() // Resetear formulario
        resultado = null
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        Text(
            text = "Sistema de Gestión de Préstamos",
            fontSize = 22.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.fillMaxWidth(),
            textAlign = TextAlign.Center
        )

        Spacer(modifier = Modifier.height(16.dp))

        // Formulario de préstamo
        Card(
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(8.dp),
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp)
            ) {
                Text(
                    text = "Crear Nuevo Préstamo",
                    fontSize = 18.sp,
                    fontWeight = FontWeight.SemiBold
                )

                Spacer(modifier = Modifier.height(16.dp))

                // Campo: Monto del préstamo
                OutlinedTextField(
                    value = if (formData.montoPrestamo == 0.0) "" else formData.montoPrestamo.toString(),
                    onValueChange = {
                        formData = formData.copy(
                            montoPrestamo = it.toDoubleOrNull() ?: 0.0
                        )
                    },
                    label = { Text("Monto del Préstamo") },
                    modifier = Modifier.fillMaxWidth(),
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Decimal)
                )

                Spacer(modifier = Modifier.height(8.dp))

                // Campo: Número de cuotas
                OutlinedTextField(
                    value = if (formData.cuotas == 0) "" else formData.cuotas.toString(),
                    onValueChange = {
                        formData = formData.copy(
                            cuotas = it.toIntOrNull() ?: 0
                        )
                    },
                    label = { Text("Número de Cuotas") },
                    modifier = Modifier.fillMaxWidth(),
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
                )

                Spacer(modifier = Modifier.height(8.dp))

                // Campo: Tasa de interés
                OutlinedTextField(
                    value = if (formData.tasa == 0.0) "" else formData.tasa.toString(),
                    onValueChange = {
                        formData = formData.copy(
                            tasa = it.toDoubleOrNull() ?: 0.0
                        )
                    },
                    label = { Text("Tasa de Interés (%)") },
                    modifier = Modifier.fillMaxWidth(),
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Decimal)
                )

                Spacer(modifier = Modifier.height(8.dp))

                // Campo: Tipo de interés (dropdown)
                var expandedTipoInteres by remember { mutableStateOf(false) }

                ExposedDropdownMenuBox(
                    expanded = expandedTipoInteres,
                    onExpandedChange = { expandedTipoInteres = it }
                ) {
                    OutlinedTextField(
                        value = formData.tipoInteres,
                        onValueChange = {},
                        readOnly = true,
                        label = { Text("Tipo de Interés") },
                        trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = expandedTipoInteres) },
                        modifier = Modifier.fillMaxWidth().menuAnchor()
                    )

                    ExposedDropdownMenu(
                        expanded = expandedTipoInteres,
                        onDismissRequest = { expandedTipoInteres = false }
                    ) {
                        tiposInteres.forEach { tipo ->
                            DropdownMenuItem(
                                text = { Text(tipo) },
                                onClick = {
                                    formData = formData.copy(tipoInteres = tipo)
                                    expandedTipoInteres = false
                                }
                            )
                        }
                    }
                }

                Spacer(modifier = Modifier.height(8.dp))

                // Campo: Periodicidad (dropdown)
                var expandedPeriodicidad by remember { mutableStateOf(false) }

                ExposedDropdownMenuBox(
                    expanded = expandedPeriodicidad,
                    onExpandedChange = { expandedPeriodicidad = it }
                ) {
                    OutlinedTextField(
                        value = periodicidades.find { it.first == formData.periodicidad }?.second ?: "Mensual",
                        onValueChange = {},
                        readOnly = true,
                        label = { Text("Periodicidad") },
                        trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = expandedPeriodicidad) },
                        modifier = Modifier.fillMaxWidth().menuAnchor()
                    )

                    ExposedDropdownMenu(
                        expanded = expandedPeriodicidad,
                        onDismissRequest = { expandedPeriodicidad = false }
                    ) {
                        periodicidades.forEach { periodo ->
                            DropdownMenuItem(
                                text = { Text(periodo.second) },
                                onClick = {
                                    formData = formData.copy(periodicidad = periodo.first)
                                    expandedPeriodicidad = false
                                }
                            )
                        }
                    }
                }

                Spacer(modifier = Modifier.height(8.dp))

                // Campo: Método de cálculo (dropdown)
                var expandedMetodoCalculo by remember { mutableStateOf(false) }

                ExposedDropdownMenuBox(
                    expanded = expandedMetodoCalculo,
                    onExpandedChange = { expandedMetodoCalculo = it }
                ) {
                    OutlinedTextField(
                        value = formData.metodoCalculo,
                        onValueChange = {},
                        readOnly = true,
                        label = { Text("Método de Cálculo") },
                        trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = expandedMetodoCalculo) },
                        modifier = Modifier.fillMaxWidth().menuAnchor()
                    )

                    ExposedDropdownMenu(
                        expanded = expandedMetodoCalculo,
                        onDismissRequest = { expandedMetodoCalculo = false }
                    ) {
                        metodosCalculo.forEach { metodo ->
                            DropdownMenuItem(
                                text = { Text(metodo) },
                                onClick = {
                                    formData = formData.copy(metodoCalculo = metodo)
                                    expandedMetodoCalculo = false
                                }
                            )
                        }
                    }
                }

                Spacer(modifier = Modifier.height(16.dp))

                // Botones
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    Button(
                        onClick = {
                            resultado = calcularPrestamo()
                        },
                        modifier = Modifier.weight(1f)
                    ) {
                        Text("Calcular")
                    }

                    Button(
                        onClick = { guardarPrestamo() },
                        modifier = Modifier.weight(1f)
                    ) {
                        Text("Guardar Préstamo")
                    }
                }
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Mostrar resultado del cálculo si existe
        resultado?.let { res ->
            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(8.dp),
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp)
                ) {
                    Text(
                        text = "Resultado del Cálculo",
                        fontSize = 18.sp,
                        fontWeight = FontWeight.SemiBold
                    )

                    Spacer(modifier = Modifier.height(8.dp))

                    Row(modifier = Modifier.fillMaxWidth()) {
                        Column(modifier = Modifier.weight(1f)) {
                            Text("Monto solicitado:", fontWeight = FontWeight.Medium)
                            Text("$${String.format("%.2f", formData.montoPrestamo)}")
                        }
                        Column(modifier = Modifier.weight(1f)) {
                            Text("Interés total:", fontWeight = FontWeight.Medium)
                            Text("$${String.format("%.2f", res.interes)}")
                        }
                    }

                    Spacer(modifier = Modifier.height(8.dp))

                    Row(modifier = Modifier.fillMaxWidth()) {
                        Column(modifier = Modifier.weight(1f)) {
                            Text("Monto total a pagar:", fontWeight = FontWeight.Medium)
                            Text("$${String.format("%.2f", res.total)}")
                        }
                        Column(modifier = Modifier.weight(1f)) {
                            Text("Cuota mensual:", fontWeight = FontWeight.Medium)
                            Text("$${String.format("%.2f", res.cuotaMensual)}")
                        }
                    }

                    Spacer(modifier = Modifier.height(8.dp))

                    Text("Detalles:", fontWeight = FontWeight.Medium)
                    Text(res.detalles)
                }
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Lista de préstamos guardados
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f),
            shape = RoundedCornerShape(8.dp),
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(16.dp)
            ) {
                Text(
                    text = "Préstamos Guardados",
                    fontSize = 18.sp,
                    fontWeight = FontWeight.SemiBold
                )

                Spacer(modifier = Modifier.height(8.dp))

                if (prestamos.isEmpty()) {
                    Box(
                        modifier = Modifier.fillMaxWidth(),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = "No hay préstamos guardados",
                            color = Color.Gray
                        )
                    }
                } else {
                    LazyColumn(
                        modifier = Modifier.fillMaxWidth(),
                        verticalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        items(prestamos) { prestamo ->
                            Card(
                                modifier = Modifier.fillMaxWidth(),
                                shape = RoundedCornerShape(4.dp),
                            ) {
                                Column(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .padding(12.dp)
                                ) {
                                    Row(
                                        modifier = Modifier.fillMaxWidth(),
                                        horizontalArrangement = Arrangement.SpaceBetween
                                    ) {
                                        Text(
                                            text = "Préstamo #${prestamo.id}",
                                            fontWeight = FontWeight.Medium
                                        )
                                        Text(
                                            text = prestamo.fecha,
                                            fontSize = 12.sp,
                                            color = Color.Gray
                                        )
                                    }

                                    Spacer(modifier = Modifier.height(4.dp))

                                    Text("Monto: $${String.format("%.2f", prestamo.dto.montoPrestamo)}")
                                    Text("Cuotas: ${prestamo.dto.cuotas}")
                                    Text("Tasa: ${prestamo.dto.tasa}%")
                                    Text("Tipo: ${prestamo.dto.tipoInteres}")
                                    Text("Total: $${String.format("%.2f", prestamo.resultado.total)}")
                                    Text("Interés: $${String.format("%.2f", prestamo.resultado.interes)}")
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}

// Funciones para el cálculo de préstamos
private fun calcularSimple(dto: PrestamoDto): ResultadoCalculo {
    val interes = dto.montoPrestamo * (dto.tasa / 100) * dto.cuotas / 12
    val total = dto.montoPrestamo + interes
    return ResultadoCalculo(
        interes = interes,
        total = total,
        cuotaMensual = total / dto.cuotas,
        detalles = "Cálculo con interés simple"
    )
}

private fun calcularCompuesto(dto: PrestamoDto): ResultadoCalculo {
    val tasaMensual = dto.tasa / 100 / 12
    val total = dto.montoPrestamo * (1 + tasaMensual).pow(dto.cuotas)
    return ResultadoCalculo(
        interes = total - dto.montoPrestamo,
        total = total,
        cuotaMensual = total / dto.cuotas,
        detalles = "Cálculo con interés compuesto"
    )
}

private fun calcularFrances(dto: PrestamoDto): ResultadoCalculo {
    val tasaMensual = dto.tasa / 100 / 12
    val cuotaMensual = dto.montoPrestamo * tasaMensual * (1 + tasaMensual).pow(dto.cuotas) /
            ((1 + tasaMensual).pow(dto.cuotas) - 1)

    val cuotas = mutableListOf<CuotaDetalle>()
    var saldoPendiente = dto.montoPrestamo

    for (i in 1..dto.cuotas) {
        val interesMensual = saldoPendiente * tasaMensual
        val amortizacion = cuotaMensual - interesMensual

        cuotas.add(
            CuotaDetalle(
                numeroCuota = i,
                monto = cuotaMensual,
                amortizacion = amortizacion,
                interes = interesMensual
            )
        )

        saldoPendiente -= amortizacion
    }

    return ResultadoCalculo(
        interes = (cuotaMensual * dto.cuotas) - dto.montoPrestamo,
        total = cuotaMensual * dto.cuotas,
        cuotaMensual = cuotaMensual,
        detalles = "Sistema francés (cuota fija)",
        cuotas = cuotas
    )
}

private fun calcularAleman(dto: PrestamoDto): ResultadoCalculo {
    val amortizacionMensual = dto.montoPrestamo / dto.cuotas
    val cuotas = mutableListOf<CuotaDetalle>()
    var saldoPendiente = dto.montoPrestamo
    var total = 0.0

    for (i in 1..dto.cuotas) {
        val interesMensual = saldoPendiente * (dto.tasa / 100 / 12)
        val cuota = amortizacionMensual + interesMensual
        total += cuota

        cuotas.add(
            CuotaDetalle(
                numeroCuota = i,
                monto = cuota,
                amortizacion = amortizacionMensual,
                interes = interesMensual
            )
        )

        saldoPendiente -= amortizacionMensual
    }

    return ResultadoCalculo(
        interes = total - dto.montoPrestamo,
        total = total,
        cuotaMensual = cuotas.first().monto, // Primera cuota como referencia
        detalles = "Sistema alemán (amortización constante)",
        cuotas = cuotas
    )
}

private fun calcularGradienteAritmetico(dto: PrestamoDto): ResultadoCalculo {
    // Implementación simplificada para demostración
    val tasaMensual = dto.tasa / 100 / 12
    val cuotaInicial = dto.montoPrestamo * tasaMensual * 0.8
    val incremento = cuotaInicial * 0.05
    var total = 0.0
    val cuotas = mutableListOf<CuotaDetalle>()

    for (i in 1..dto.cuotas) {
        val cuotaActual = cuotaInicial + (incremento * (i - 1))
        total += cuotaActual

        cuotas.add(
            CuotaDetalle(
                numeroCuota = i,
                monto = cuotaActual
            )
        )
    }

    return ResultadoCalculo(
        interes = total - dto.montoPrestamo,
        total = total,
        cuotaMensual = total / dto.cuotas, // Promedio como referencia
        detalles = "Gradiente aritmético (incremento constante)",
        cuotas = cuotas
    )
}

private fun calcularGradienteGeometrico(dto: PrestamoDto): ResultadoCalculo {
    // Implementación simplificada para demostración
    val tasaMensual = dto.tasa / 100 / 12
    val cuotaInicial = dto.montoPrestamo * tasaMensual * 0.7
    val factorCrecimiento = 1.03
    var total = 0.0
    val cuotas = mutableListOf<CuotaDetalle>()

    for (i in 1..dto.cuotas) {
        val cuotaActual = cuotaInicial * factorCrecimiento.pow(i - 1)
        total += cuotaActual

        cuotas.add(
            CuotaDetalle(
                numeroCuota = i,
                monto = cuotaActual
            )
        )
    }

    return ResultadoCalculo(
        interes = total - dto.montoPrestamo,
        total = total,
        cuotaMensual = total / dto.cuotas, // Promedio como referencia
        detalles = "Gradiente geométrico (incremento proporcional)",
        cuotas = cuotas
    )
}