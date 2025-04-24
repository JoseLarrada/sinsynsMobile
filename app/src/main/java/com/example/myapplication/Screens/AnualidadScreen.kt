package com.example.myapplication.Screens

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.expandVertically
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.shrinkVertically
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.selection.selectable
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ExpandLess
import androidx.compose.material.icons.filled.ExpandMore
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.AttachMoney
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material.icons.filled.Error
import androidx.compose.material.icons.filled.Percent
import androidx.compose.material.icons.filled.Schedule
import androidx.compose.ui.text.style.TextAlign
import java.text.NumberFormat

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AnualidadesScreen(navController: NavHostController) {
    var tasaInteres by remember { mutableStateOf("") }
    var numeroPeriodos by remember { mutableStateOf("") }
    var valorAnualidad by remember { mutableStateOf("") }
    var resultado by remember { mutableStateOf("") }
    var expanded by remember { mutableStateOf(false) }
    var expandedMenu by remember { mutableStateOf(false) }
    var periodicidad by remember { mutableStateOf("Anual") }
    var tipoCalculo by remember { mutableStateOf("Valor Futuro") }
    var mostrarError by remember { mutableStateOf(false) }
    var mensajeError by remember { mutableStateOf("") }

    val opcionesPeriodicidad = listOf("Mensual", "Bimestral", "Trimestral", "Semestral", "Anual")
    val opcionesTipoCalculo = listOf("Valor Futuro", "Valor Presente")
    val scrollState = rememberScrollState()

    val calcularAnualidad = {
        if (tasaInteres.isEmpty() || numeroPeriodos.isEmpty() || valorAnualidad.isEmpty()) {
            mostrarError = true
            mensajeError = "Por favor, complete todos los campos."
        } else {
            try {
                val anualidad = valorAnualidad.toDouble()
                var tasa = tasaInteres.toDouble() / 100 // Convertir porcentaje a decimal
                val periodos = numeroPeriodos.toInt()

                // Ajustar tasa según periodicidad
                tasa = when (periodicidad) {
                    "Mensual" -> tasa / 12
                    "Bimestral" -> tasa / 6
                    "Trimestral" -> tasa / 4
                    "Semestral" -> tasa / 2
                    else -> tasa
                }

                val resultadoNumerico = if (tipoCalculo == "Valor Futuro") {
                    anualidad * ((Math.pow(1 + tasa, periodos.toDouble()) - 1) / tasa)
                } else {
                    anualidad * ((1 - Math.pow(1 + tasa, -periodos.toDouble())) / tasa)
                }

                val formatoMoneda = NumberFormat.getCurrencyInstance()
                resultado = "${tipoCalculo}: ${formatoMoneda.format(resultadoNumerico)}"
                mostrarError = false
            } catch (e: Exception) {
                mostrarError = true
                mensajeError = "Error en los datos ingresados. Verifique que sean valores numéricos válidos."
            }
        }
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Cálculo de Anualidades") },
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
            // Explicación de anualidades
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
                            "¿Qué son las Anualidades?",
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
                                text = "Las anualidades son una serie de pagos iguales que se realizan en intervalos regulares de tiempo. " +
                                        "Pueden ser ordinarias (al final del período) o anticipadas (al inicio del período).",
                                fontSize = 14.sp,
                                lineHeight = 20.sp
                            )

                            Spacer(modifier = Modifier.height(12.dp))

                            Text(
                                text = "Fórmulas:",
                                fontWeight = FontWeight.Bold,
                                fontSize = 14.sp
                            )

                            Spacer(modifier = Modifier.height(4.dp))

                            Text(
                                text = "• Valor Futuro: VF = A × ((1 + i)ⁿ - 1) / i",
                                fontSize = 14.sp
                            )

                            Spacer(modifier = Modifier.height(4.dp))

                            Text(
                                text = "• Valor Presente: VP = A × (1 - (1 + i)⁻ⁿ) / i",
                                fontSize = 14.sp
                            )

                            Spacer(modifier = Modifier.height(8.dp))

                            Text(
                                text = "Donde:\nA = Anualidad\ni = Tasa de interés\nn = Número de períodos",
                                fontSize = 14.sp,
                                lineHeight = 18.sp
                            )
                        }
                    }
                }
            }

            // Tipo de cálculo
            Card(
                modifier = Modifier.fillMaxWidth(),
                elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
                colors = CardDefaults.cardColors(containerColor = Color.White)
            ) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Text(
                        text = "Tipo de Cálculo",
                        fontWeight = FontWeight.Bold,
                        fontSize = 16.sp,
                        modifier = Modifier.padding(bottom = 8.dp)
                    )

                    Row(modifier = Modifier.fillMaxWidth()) {
                        opcionesTipoCalculo.forEach { opcion ->
                            Row(
                                verticalAlignment = Alignment.CenterVertically,
                                modifier = Modifier
                                    .weight(1f)
                                    .selectable(
                                        selected = (tipoCalculo == opcion),
                                        onClick = { tipoCalculo = opcion }
                                    )
                                    .padding(8.dp)
                            ) {
                                RadioButton(
                                    selected = (tipoCalculo == opcion),
                                    onClick = { tipoCalculo = opcion },
                                    colors = RadioButtonDefaults.colors(
                                        selectedColor = Color(0xFF9130F2)
                                    )
                                )
                                Text(
                                    text = opcion,
                                    modifier = Modifier.padding(start = 8.dp)
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
                        text = "Ingrese los datos",
                        fontWeight = FontWeight.Bold,
                        fontSize = 16.sp
                    )

                    OutlinedTextField(
                        value = valorAnualidad,
                        onValueChange = { valorAnualidad = it },
                        label = { Text("Anualidad") },
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
                        value = tasaInteres,
                        onValueChange = { tasaInteres = it },
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

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(16.dp)
                    ) {
                        // Periodicidad
                        ExposedDropdownMenuBox(
                            expanded = expandedMenu,
                            onExpandedChange = { expandedMenu = !expandedMenu },
                            modifier = Modifier.weight(1f)
                        ) {
                            OutlinedTextField(
                                value = periodicidad,
                                onValueChange = {},
                                readOnly = true,
                                label = { Text("Periodicidad") },
                                leadingIcon = { Icon(Icons.Default.Schedule, contentDescription = null) },
                                trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = expandedMenu) },
                                colors = OutlinedTextFieldDefaults.colors(
                                    focusedBorderColor = Color(0xFF9130F2),
                                    focusedLabelColor = Color(0xFF9130F2)
                                ),
                                modifier = Modifier.menuAnchor()
                            )

                            ExposedDropdownMenu(
                                expanded = expandedMenu,
                                onDismissRequest = { expandedMenu = false }
                            ) {
                                opcionesPeriodicidad.forEach { opcion ->
                                    DropdownMenuItem(
                                        text = { Text(opcion) },
                                        onClick = {
                                            periodicidad = opcion
                                            expandedMenu = false
                                        }
                                    )
                                }
                            }
                        }

                        // Períodos
                        OutlinedTextField(
                            value = numeroPeriodos,
                            onValueChange = { numeroPeriodos = it },
                            label = { Text("Períodos") },
                            leadingIcon = { Icon(Icons.Default.DateRange, contentDescription = null) },
                            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                            colors = OutlinedTextFieldDefaults.colors(
                                focusedBorderColor = Color(0xFF9130F2),
                                focusedLabelColor = Color(0xFF9130F2),
                                cursorColor = Color(0xFF9130F2)
                            ),
                            modifier = Modifier.weight(1f),
                            singleLine = true
                        )
                    }
                }
            }

            // Botón calcular
            Button(
                onClick = calcularAnualidad,
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

            // Resultado
            AnimatedVisibility(
                visible = resultado.isNotEmpty() || mostrarError,
                enter = fadeIn() + expandVertically(),
                exit = fadeOut() + shrinkVertically()
            ) {
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    colors = CardDefaults.cardColors(
                        containerColor = if (mostrarError) Color(0xFFFDECEE) else Color(0xFFEAE0FD)
                    ),
                    shape = RoundedCornerShape(12.dp)
                ) {
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(16.dp),
                        contentAlignment = Alignment.Center
                    ) {
                        if (mostrarError) {
                            Row(
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
                        } else {
                            Text(
                                text = resultado,
                                fontSize = 18.sp,
                                fontWeight = FontWeight.Bold,
                                color = Color(0xFF9130F2),
                                textAlign = TextAlign.Center
                            )
                        }
                    }
                }
            }

            Spacer(modifier = Modifier.height(16.dp))
        }
    }
}
