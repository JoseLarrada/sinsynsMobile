package com.example.myapplication.Screens

import InteresViewModel
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ExpandLess
import androidx.compose.material.icons.filled.ExpandMore
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.graphics.Color
import androidx.navigation.NavHostController
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.text.input.KeyboardType
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.compose.animation.expandVertically
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.shrinkVertically
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
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.filled.AttachMoney
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material.icons.filled.Functions
import androidx.compose.material.icons.filled.MonetizationOn
import androidx.compose.material.icons.filled.Percent
import androidx.compose.material.icons.filled.Schedule
import androidx.compose.ui.graphics.vector.ImageVector

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun InteresSimpleScreen(navController: NavHostController) {
    // Estados
    var tasaInteres by remember { mutableStateOf("") }
    var tiempo by remember { mutableStateOf("") }
    var valorFinal by remember { mutableStateOf("") }
    var valorPresente by remember { mutableStateOf("") }
    var expandedInfo by remember { mutableStateOf(false) }
    var expandedPeriodicidad by remember { mutableStateOf(false) }

    val tasaInteresDecimal = tasaInteres.toDoubleOrNull()?.let {
        it / 100 // Dividir por 100 para convertir el porcentaje a decimal
    } ?: 0.0

    // Opción de periodicidad
    val opcionesPeriodicidad = listOf("Mensual", "Bimestral", "Trimestral", "Semestral", "Anual")
    var periodicidadSeleccionada by remember { mutableStateOf(opcionesPeriodicidad.last()) }

    // ViewModel y resultados
    val viewModel: InteresViewModel = viewModel()
    val interesResponse by viewModel.interesSimpleResponse.observeAsState()

    // Resultados calculados
    var resultadoVisible by remember { mutableStateOf(false) }
    var tasaInteresObj by remember { mutableStateOf("") }
    var tiempoObj by remember { mutableStateOf("") }
    var valorPresenteObj by remember { mutableStateOf("") }

    LaunchedEffect(interesResponse) {
        interesResponse?.let {
            valorFinal = it.interesSimple.toString()
            tasaInteresObj = it.tasaInteres.toString()
            tiempoObj = it.tiempo.toString()
            valorPresenteObj = it.valorPresente.toString()
            resultadoVisible = true
        }
    }

    // Función de validación: permite solo números y un punto decimal
    fun validateInput(input: String): String {
        return input.filterIndexed { index, c ->
            c.isDigit() || (c == '.' && input.indexOf('.') == index)
        }
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Cálculo de Tasas de interés simple") },
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
        val scrollState = rememberScrollState()

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(horizontal = 16.dp)
                .verticalScroll(scrollState) // 👈 scroll vertical activado
                .imePadding()
                .background(Color(0xFFF8F5FF)),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            Spacer(modifier = Modifier.height(8.dp))

            // Tarjeta con información
            Card(
                modifier = Modifier.fillMaxWidth(),
                elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
                colors = CardDefaults.cardColors(containerColor = Color.White)
            ) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            "¿Qué es el Interés Simple?",
                            fontWeight = FontWeight.Bold,
                            fontSize = 18.sp,
                            color = Color(0xFF1D1B20)
                        )
                        IconButton(onClick = { expandedInfo = !expandedInfo }) {
                            Icon(
                                imageVector = if (expandedInfo) Icons.Filled.ExpandLess else Icons.Filled.ExpandMore,
                                contentDescription = if (expandedInfo) "Contraer" else "Expandir",
                                tint = Color(0xFF9130F2)
                            )
                        }
                    }

                    AnimatedVisibility(
                        visible = expandedInfo,
                        enter = expandVertically() + fadeIn(),
                        exit = shrinkVertically() + fadeOut()
                    ) {
                        Column {
                            Spacer(modifier = Modifier.height(8.dp))
                            Text(
                                text = "El interés simple es el pago por el uso de dinero prestado. Se calcula multiplicando la tasa de interés por el tiempo y el valor presente.",
                                fontSize = 15.sp,
                                color = Color(0xFF44464F),
                                lineHeight = 22.sp
                            )

                            Spacer(modifier = Modifier.height(16.dp))

                            Card(
                                modifier = Modifier.fillMaxWidth(),
                                colors = CardDefaults.cardColors(containerColor = Color(0xFFEFE8FD))
                            ) {
                                Column(modifier = Modifier.padding(16.dp)) {
                                    Text(
                                        text = "Fórmula:",
                                        fontWeight = FontWeight.Bold,
                                        color = Color(0xFF1D1B20)
                                    )
                                    Text(
                                        text = "I = VP × i × t",
                                        fontWeight = FontWeight.Bold,
                                        fontSize = 18.sp,
                                        color = Color(0xFF9130F2),
                                        modifier = Modifier.padding(vertical = 8.dp)
                                    )

                                    Text(
                                        text = "Donde:",
                                        color = Color(0xFF1D1B20),
                                    )
                                    Text(
                                        text = "I = Interés\nVP = Valor Presente\ni = Tasa de interés\nt = Tiempo",
                                        color = Color(0xFF44464F),
                                        lineHeight = 20.sp
                                    )

                                    Spacer(modifier = Modifier.height(12.dp))

                                    Text(
                                        text = "Ejemplo: Si inviertes $1,000 con una tasa del 5% anual durante 3 años:\nI = 1000 × 0.05 × 3 = $150",
                                        color = Color(0xFF44464F),
                                        lineHeight = 20.sp
                                    )
                                }
                            }
                        }
                    }
                }
            }

            // Campos de entrada
            Text(
                text = "Ingresa los datos",
                fontSize = 16.sp,
                fontWeight = FontWeight.Medium,
                color = Color(0xFF1D1B20)
            )

            // Periodicidad
            ExposedDropdownMenuBox(
                expanded = expandedPeriodicidad,
                onExpandedChange = { expandedPeriodicidad = !expandedPeriodicidad }
            ) {
                OutlinedTextField(
                    value = periodicidadSeleccionada,
                    onValueChange = {},
                    readOnly = true,
                    label = { Text("Periodicidad") },
                    trailingIcon = {
                        ExposedDropdownMenuDefaults.TrailingIcon(expanded = expandedPeriodicidad)
                    },
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedBorderColor = Color(0xFF9130F2),
                        unfocusedBorderColor = Color(0xFFCAC4D0),
                        focusedLabelColor = Color(0xFF9130F2)
                    ),
                    modifier = Modifier
                        .fillMaxWidth()
                        .menuAnchor()
                )

                ExposedDropdownMenu(
                    expanded = expandedPeriodicidad,
                    onDismissRequest = { expandedPeriodicidad = false }
                ) {
                    opcionesPeriodicidad.forEach { opcion ->
                        DropdownMenuItem(
                            text = { Text(opcion) },
                            onClick = {
                                periodicidadSeleccionada = opcion
                                expandedPeriodicidad = false
                            }
                        )
                    }
                }
            }

            OutlinedTextField(
                value = tasaInteres,
                onValueChange = { tasaInteres = validateInput(it) },
                label = { Text("Tasa de interés (%)") },
                keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Number),
                colors = OutlinedTextFieldDefaults.colors(
                    focusedBorderColor = Color(0xFF9130F2),
                    unfocusedBorderColor = Color(0xFFCAC4D0),
                    focusedLabelColor = Color(0xFF9130F2)
                ),
                leadingIcon = {
                    Icon(
                        imageVector = Icons.Default.Percent,
                        contentDescription = null,
                        tint = Color(0xFF9130F2)
                    )
                },
                modifier = Modifier.fillMaxWidth()
            )

            OutlinedTextField(
                value = tiempo,
                onValueChange = { tiempo = validateInput(it) },
                label = { Text("Tiempo (años)") },
                keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Number),
                colors = OutlinedTextFieldDefaults.colors(
                    focusedBorderColor = Color(0xFF9130F2),
                    unfocusedBorderColor = Color(0xFFCAC4D0),
                    focusedLabelColor = Color(0xFF9130F2)
                ),
                leadingIcon = {
                    Icon(
                        imageVector = Icons.Default.Schedule,
                        contentDescription = null,
                        tint = Color(0xFF9130F2)
                    )
                },
                modifier = Modifier.fillMaxWidth()
            )

            OutlinedTextField(
                value = valorPresente,
                onValueChange = { valorPresente = validateInput(it) },
                label = { Text("Valor Presente (Capital)") },
                keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Number),
                colors = OutlinedTextFieldDefaults.colors(
                    focusedBorderColor = Color(0xFF9130F2),
                    unfocusedBorderColor = Color(0xFFCAC4D0),
                    focusedLabelColor = Color(0xFF9130F2)
                ),
                leadingIcon = {
                    Icon(
                        imageVector = Icons.Default.AttachMoney,
                        contentDescription = null,
                        tint = Color(0xFF9130F2)
                    )
                },
                modifier = Modifier.fillMaxWidth()
            )

            OutlinedTextField(
                value = valorFinal,
                onValueChange = { valorFinal = validateInput(it) },
                label = { Text("Valor Final (opcional)") },
                keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Number),
                colors = OutlinedTextFieldDefaults.colors(
                    focusedBorderColor = Color(0xFF9130F2),
                    unfocusedBorderColor = Color(0xFFCAC4D0),
                    focusedLabelColor = Color(0xFF9130F2)
                ),
                leadingIcon = {
                    Icon(
                        imageVector = Icons.Default.MonetizationOn,
                        contentDescription = null,
                        tint = Color(0xFF9130F2)
                    )
                },
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(8.dp))

            // Botón de Calcular con gradiente
            Button(
                onClick = {
                    viewModel.calcularInteresSimple(tasaInteresDecimal.toString(), tiempo, valorFinal, valorPresente)
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(56.dp),
                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF9130F2)),
                shape = RoundedCornerShape(12.dp)
            ) {
                Text(
                    "CALCULAR INTERÉS",
                    fontWeight = FontWeight.Bold,
                    fontSize = 16.sp
                )
            }

            Spacer(modifier = Modifier.height(8.dp))

            // Mostrar resultados
            AnimatedVisibility(
                visible = resultadoVisible,
                enter = expandVertically() + fadeIn(),
                exit = shrinkVertically() + fadeOut()
            ) {
                Card(
                    colors = CardDefaults.cardColors(containerColor = Color(0xFFEFE8FD)),
                    shape = RoundedCornerShape(12.dp),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Column(
                        modifier = Modifier.padding(16.dp),
                        verticalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        Text(
                            text = "Resultados del cálculo",
                            fontSize = 18.sp,
                            fontWeight = FontWeight.Bold,
                            color = Color(0xFF1D1B20)
                        )

                        Divider(color = Color(0xFFCAC4D0), thickness = 1.dp)

                        ResultadoItem(
                            label = "Interés Calculado:",
                            valor = valorFinal,
                            icon = Icons.Default.Functions
                        )

                        ResultadoItem(
                            label = "Tasa de interés:",
                            valor = "$tasaInteresObj%",
                            icon = Icons.Default.Percent
                        )

                        ResultadoItem(
                            label = "Tiempo:",
                            valor = "$tiempoObj años",
                            icon = Icons.Default.Schedule
                        )

                        ResultadoItem(
                            label = "Capital:",
                            valor = "$$valorPresenteObj",
                            icon = Icons.Default.AttachMoney
                        )

                        ResultadoItem(
                            label = "Periodicidad:",
                            valor = periodicidadSeleccionada,
                            icon = Icons.Default.DateRange
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun ResultadoItem(label: String, valor: String, icon: ImageVector) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(
            imageVector = icon,
            contentDescription = null,
            tint = Color(0xFF9130F2),
            modifier = Modifier.size(24.dp)
        )

        Spacer(modifier = Modifier.width(12.dp))

        Column {
            Text(
                text = label,
                fontSize = 14.sp,
                color = Color(0xFF44464F)
            )
            Text(
                text = valor,
                fontSize = 18.sp,
                fontWeight = FontWeight.Medium,
                color = Color(0xFF1D1B20)
            )
        }
    }
}

