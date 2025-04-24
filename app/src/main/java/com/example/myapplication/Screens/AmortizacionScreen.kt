package com.example.myapplication.Screens

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.expandVertically
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.shrinkVertically
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.AttachMoney
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material.icons.filled.Error
import androidx.compose.material.icons.filled.ExpandLess
import androidx.compose.material.icons.filled.ExpandMore
import androidx.compose.material.icons.filled.Payments
import androidx.compose.material.icons.filled.Percent
import androidx.compose.material.icons.filled.Schedule
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.example.myapplication.API.Amortizacion.AmortizacionFila
import com.example.myapplication.viewModel.AmortizacionViewModel
import java.text.NumberFormat

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AmortizacionScreen(navController: NavHostController) {
    var monto by remember { mutableStateOf("") }
    var tasa by remember { mutableStateOf("") }
    var periodos by remember { mutableStateOf("") }
    var resultado by remember { mutableStateOf("") }
    var expanded by remember { mutableStateOf(false) }
    var mostrarTabla by remember { mutableStateOf(false) }
    var mostrarError by remember { mutableStateOf(false) }
    var mensajeError by remember { mutableStateOf("") }

    val viewModel: AmortizacionViewModel = viewModel()
    val resultadoFrancesa by viewModel.amortizacionFrancesa.observeAsState()
    val resultadoAlemana by viewModel.amortizacionAlemana.observeAsState()
    val resultadoAmericana by viewModel.amortizacionAmericana.observeAsState()

    var metodoSeleccionado by remember { mutableStateOf("Francés") }
    val metodos = listOf("Francés", "Alemán", "Inglés")
    var metodoMenuExpanded by remember { mutableStateOf(false) }

    var periodicidad by remember { mutableStateOf("Mensual") }
    val opcionesPeriodicidad = listOf("Mensual", "Bimestral", "Trimestral", "Semestral", "Anual")
    var periodicidadMenuExpanded by remember { mutableStateOf(false) }

    val scrollState = rememberScrollState()
    val datosTabla = remember { mutableStateListOf<AmortizacionFila>() }

    // Observadores para llenar datosTabla
    LaunchedEffect(resultadoFrancesa) {
        if (metodoSeleccionado == "Francés" && resultadoFrancesa != null) {
            datosTabla.clear()
            datosTabla.addAll(resultadoFrancesa!!.tabla)
        }
    }
    LaunchedEffect(resultadoAlemana) {
        if (metodoSeleccionado == "Alemán" && resultadoAlemana != null) {
            datosTabla.clear()
            datosTabla.addAll(resultadoAlemana!!.tabla)
        }
    }
    LaunchedEffect(resultadoAmericana) {
        if (metodoSeleccionado == "Inglés" && resultadoAmericana != null) {
            datosTabla.clear()
            datosTabla.addAll(resultadoAmericana!!.tabla)
        }
    }

    val calcularAmortizacion = {
        if (monto.isEmpty() || tasa.isEmpty() || periodos.isEmpty()) {
            mostrarError = true
            mensajeError = "Por favor, complete todos los campos."
            mostrarTabla = false
        } else {
            try {
                val montoInicial = monto.toDouble()
                var tasaInteres = tasa.toDouble() / 100
                val numPeriodos = periodos.toInt()

                """asaInteres = when (periodicidad) {
                    "Mensual" -> tasaInteres / 12
                    "Bimestral" -> tasaInteres / 6
                    "Trimestral" -> tasaInteres / 4
                    "Semestral" -> tasaInteres / 2
                    else -> tasaInteres
                }"""

                datosTabla.clear()

                when (metodoSeleccionado) {
                    "Francés" -> {
                        viewModel.calcularAmortizacionFrancesa(montoInicial, tasaInteres, numPeriodos)
                    }
                    "Alemán" -> {
                        viewModel.calcularAmortizacionAlemana(montoInicial, tasaInteres, numPeriodos)
                    }
                    "Inglés" -> {
                        viewModel.calcularAmortizacionAmericana(montoInicial, tasaInteres, numPeriodos)
                    }
                }

                mostrarError = false
                mostrarTabla = true

            } catch (e: Exception) {
                mostrarError = true
                mensajeError = "Error en los datos ingresados. Verifique que sean valores numéricos válidos."
                mostrarTabla = false
            }
        }
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Tabla de Amortización") },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(
                            imageVector = Icons.Default.ArrowBack,
                            contentDescription = "Regresar",
                            tint = Color.White
                        )
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = Color(0xFF9130F2),
                    titleContentColor = Color.White
                )
            )
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(16.dp)
                .verticalScroll(scrollState),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            // Explicación de amortización
            Card(
                modifier = Modifier.fillMaxWidth(),
                elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
                colors = CardDefaults.cardColors(containerColor = Color(0xFFF5F5F5))
            ) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            "¿Qué es la amortización?",
                            fontWeight = FontWeight.Bold,
                            fontSize = 16.sp
                        )
                        IconButton(onClick = { expanded = !expanded }) {
                            Icon(
                                imageVector = if (expanded) Icons.Default.ExpandLess else Icons.Default.ExpandMore,
                                contentDescription = if (expanded) "Ocultar" else "Mostrar"
                            )
                        }
                    }

                    AnimatedVisibility(
                        visible = expanded,
                        enter = fadeIn() + expandVertically(),
                        exit = fadeOut() + shrinkVertically()
                    ) {
                        Column(modifier = Modifier.padding(top = 8.dp)) {
                            Text(
                                text = "La amortización consiste en el pago gradual de una deuda mediante cuotas periódicas. " +
                                        "La cuota incluye una parte de capital y otra de intereses.",
                                fontSize = 14.sp,
                                lineHeight = 20.sp
                            )

                            Spacer(modifier = Modifier.height(12.dp))

                            Text(
                                text = "Métodos de amortización:",
                                fontWeight = FontWeight.Bold,
                                fontSize = 14.sp
                            )

                            Spacer(modifier = Modifier.height(4.dp))

                            Column(verticalArrangement = Arrangement.spacedBy(4.dp)) {
                                Text(
                                    text = "• Francés: Cuotas constantes, amortización creciente e intereses decrecientes.",
                                    fontSize = 14.sp
                                )
                                Text(
                                    text = "• Alemán: Amortización constante, cuotas decrecientes.",
                                    fontSize = 14.sp
                                )
                                Text(
                                    text = "• Inglés: Solo se pagan intereses y el capital al final del plazo.",
                                    fontSize = 14.sp
                                )
                            }
                        }
                    }
                }
            }

            // Campos de entrada
            Card(
                modifier = Modifier.fillMaxWidth(),
                elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
                colors = CardDefaults.cardColors(containerColor = Color.White)
            ) {
                Column(
                    modifier = Modifier.padding(16.dp),
                    verticalArrangement = Arrangement.spacedBy(16.dp)
                ) {
                    Text(
                        text = "Ingrese los datos del préstamo",
                        fontWeight = FontWeight.Bold,
                        fontSize = 16.sp
                    )

                    OutlinedTextField(
                        value = monto,
                        onValueChange = { monto = it },
                        label = { Text("Monto del préstamo") },
                        leadingIcon = { Icon(Icons.Default.AttachMoney, contentDescription = null) },
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Decimal),
                        colors = OutlinedTextFieldDefaults.colors(
                            focusedBorderColor = Color(0xFF9130F2),
                            focusedLabelColor = Color(0xFF9130F2),
                            cursorColor = Color(0xFF9130F2)
                        ),
                        modifier = Modifier.fillMaxWidth(),
                        singleLine = true
                    )

                    OutlinedTextField(
                        value = tasa,
                        onValueChange = { tasa = it },
                        label = { Text("Tasa de interés (%)") },
                        leadingIcon = { Icon(Icons.Default.Percent, contentDescription = null) },
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Decimal),
                        colors = OutlinedTextFieldDefaults.colors(
                            focusedBorderColor = Color(0xFF9130F2),
                            focusedLabelColor = Color(0xFF9130F2),
                            cursorColor = Color(0xFF9130F2)
                        ),
                        modifier = Modifier.fillMaxWidth(),
                        singleLine = true
                    )

                    OutlinedTextField(
                        value = periodos,
                        onValueChange = { periodos = it },
                        label = { Text("Número de períodos") },
                        leadingIcon = { Icon(Icons.Default.DateRange, contentDescription = null) },
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                        colors = OutlinedTextFieldDefaults.colors(
                            focusedBorderColor = Color(0xFF9130F2),
                            focusedLabelColor = Color(0xFF9130F2),
                            cursorColor = Color(0xFF9130F2)
                        ),
                        modifier = Modifier.fillMaxWidth(),
                        singleLine = true
                    )

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(16.dp)
                    ) {
                        // Método de amortización
                        ExposedDropdownMenuBox(
                            expanded = metodoMenuExpanded,
                            onExpandedChange = { metodoMenuExpanded = !metodoMenuExpanded },
                            modifier = Modifier.weight(1f)
                        ) {
                            OutlinedTextField(
                                value = metodoSeleccionado,
                                onValueChange = {},
                                readOnly = true,
                                label = { Text("Método") },
                                leadingIcon = { Icon(Icons.Default.Payments, contentDescription = null) },
                                trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = metodoMenuExpanded) },
                                colors = OutlinedTextFieldDefaults.colors(
                                    focusedBorderColor = Color(0xFF9130F2),
                                    focusedLabelColor = Color(0xFF9130F2)
                                ),
                                modifier = Modifier.menuAnchor().fillMaxWidth()
                            )

                            ExposedDropdownMenu(
                                expanded = metodoMenuExpanded,
                                onDismissRequest = { metodoMenuExpanded = false }
                            ) {
                                metodos.forEach { metodo ->
                                    DropdownMenuItem(
                                        text = { Text(metodo) },
                                        onClick = {
                                            metodoSeleccionado = metodo
                                            metodoMenuExpanded = false
                                        }
                                    )
                                }
                            }
                        }

                        // Periodicidad
                        ExposedDropdownMenuBox(
                            expanded = periodicidadMenuExpanded,
                            onExpandedChange = { periodicidadMenuExpanded = !periodicidadMenuExpanded },
                            modifier = Modifier.weight(1f)
                        ) {
                            OutlinedTextField(
                                value = periodicidad,
                                onValueChange = {},
                                readOnly = true,
                                label = { Text("Periodicidad") },
                                leadingIcon = { Icon(Icons.Default.Schedule, contentDescription = null) },
                                trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = periodicidadMenuExpanded) },
                                colors = OutlinedTextFieldDefaults.colors(
                                    focusedBorderColor = Color(0xFF9130F2),
                                    focusedLabelColor = Color(0xFF9130F2)
                                ),
                                modifier = Modifier.menuAnchor().fillMaxWidth()
                            )

                            ExposedDropdownMenu(
                                expanded = periodicidadMenuExpanded,
                                onDismissRequest = { periodicidadMenuExpanded = false }
                            ) {
                                opcionesPeriodicidad.forEach { opcion ->
                                    DropdownMenuItem(
                                        text = { Text(opcion) },
                                        onClick = {
                                            periodicidad = opcion
                                            periodicidadMenuExpanded = false
                                        }
                                    )
                                }
                            }
                        }
                    }
                }
            }

            // Botón calcular
            Button(
                onClick = calcularAmortizacion,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(56.dp),
                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF9130F2)),
                shape = RoundedCornerShape(28.dp)
            ) {
                Text(
                    "Calcular",
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold
                )
            }

            // Mensaje de error
            AnimatedVisibility(
                visible = mostrarError,
                enter = fadeIn() + expandVertically(),
                exit = fadeOut() + shrinkVertically()
            ) {
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    colors = CardDefaults.cardColors(containerColor = Color(0xFFFDECEE)),
                    shape = RoundedCornerShape(12.dp)
                ) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(16.dp),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        Icon(
                            imageVector = Icons.Default.Error,
                            contentDescription = null,
                            tint = Color(0xFFF2637E)
                        )
                        Text(
                            text = mensajeError,
                            color = Color(0xFFF2637E),
                            fontWeight = FontWeight.Medium
                        )
                    }
                }
            }

            // Resultado y Tabla de Amortización
            AnimatedVisibility(
                visible = mostrarTabla,
                enter = fadeIn() + expandVertically(),
                exit = fadeOut() + shrinkVertically()
            ) {
                Column(verticalArrangement = Arrangement.spacedBy(16.dp)) {
                    // Resumen
                    Card(
                        modifier = Modifier.fillMaxWidth(),
                        colors = CardDefaults.cardColors(containerColor = Color(0xFFEAE0FD)),
                        shape = RoundedCornerShape(12.dp)
                    ) {
                        Column(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(16.dp),
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            Text(
                                text = resultado,
                                fontSize = 16.sp,
                                fontWeight = FontWeight.Bold,
                                color = Color(0xFF9130F2),
                                textAlign = TextAlign.Center
                            )
                        }
                    }

                    // Tabla de Amortización
                    Card(
                        modifier = Modifier.fillMaxWidth(),
                        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
                        colors = CardDefaults.cardColors(containerColor = Color.White)
                    ) {
                        Column(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(16.dp)
                        ) {
                            Text(
                                text = "Tabla de Amortización",
                                fontWeight = FontWeight.Bold,
                                fontSize = 16.sp,
                                modifier = Modifier.padding(bottom = 12.dp)
                            )

                            // Encabezados
                            Row(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .background(Color(0xFFF0F0F0))
                                    .padding(vertical = 8.dp, horizontal = 4.dp)
                            ) {
                                Text("Periodo", fontWeight = FontWeight.Bold, fontSize = 12.sp, modifier = Modifier.weight(1f), textAlign = TextAlign.End)
                                Text("Cuota", fontWeight = FontWeight.Bold, fontSize = 12.sp, modifier = Modifier.weight(1f), textAlign = TextAlign.End)
                                Text("Interes", fontWeight = FontWeight.Bold, fontSize = 12.sp, modifier = Modifier.weight(1f), textAlign = TextAlign.End)
                                Text("Amortizacion", fontWeight = FontWeight.Bold, fontSize = 12.sp, modifier = Modifier.weight(1f), textAlign = TextAlign.End)
                                Text("Saldo", fontWeight = FontWeight.Bold, fontSize = 12.sp, modifier = Modifier.weight(1f), textAlign = TextAlign.End)
                            }

                            // Filas
                            val filasAMostrar = minOf(datosTabla.size, 5)
                            for (i in 0 until filasAMostrar) {
                                val fila = datosTabla[i]
                                Row(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .padding(vertical = 8.dp, horizontal = 4.dp)
                                        .background(if (i % 2 == 0) Color.White else Color(0xFFF9F9F9))
                                ) {
                                    Text("${fila.periodo}", fontSize = 12.sp, modifier = Modifier.weight(1f), textAlign = TextAlign.End)
                                    Text(formatoCurrency(fila.cuota), fontSize = 12.sp, modifier = Modifier.weight(1f), textAlign = TextAlign.End)
                                    Text(formatoCurrency(fila.interes), fontSize = 12.sp, modifier = Modifier.weight(1f), textAlign = TextAlign.End)
                                    Text(formatoCurrency(fila.amortizacion), fontSize = 12.sp, modifier = Modifier.weight(1f), textAlign = TextAlign.End)
                                    Text(formatoCurrency(fila.saldo), fontSize = 12.sp, modifier = Modifier.weight(1f), textAlign = TextAlign.End)
                                }
                            }

                            if (datosTabla.size > 5) {
                                Text(
                                    text = "...y ${datosTabla.size - 5} períodos más.",
                                    fontSize = 12.sp,
                                    fontStyle = FontStyle.Italic,
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .padding(top = 8.dp),
                                    textAlign = TextAlign.Center
                                )
                            }
                        }
                    }
                }
            }

            Spacer(modifier = Modifier.height(16.dp))
        }
    }
}


// Función para formatear valores monetarios
fun formatoCurrency(valor: Double): String {
    return NumberFormat.getCurrencyInstance().format(valor)
}
