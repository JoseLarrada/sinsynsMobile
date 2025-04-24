package com.example.myapplication.Screens


import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.material.icons.Icons
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.graphics.Color
import androidx.navigation.NavHostController
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.material.icons.filled.Schedule
import androidx.compose.ui.Alignment
import androidx.compose.ui.focus.FocusDirection
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign


@Composable
fun TasaInteresScreen(navController: NavHostController) {
    var numeroBase by remember { mutableStateOf("") }
    var porcentaje by remember { mutableStateOf("") }
    var resultado by remember { mutableStateOf<Double?>(null) }
    var expanded by remember { mutableStateOf(false) }
    var tipoCalculo by remember { mutableStateOf("valorFuturo") } // Para los radio buttons

    val keyboardController = LocalSoftwareKeyboardController.current
    val focusManager = LocalFocusManager.current
    val scrollState = rememberScrollState()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFF8F8F8))
            .verticalScroll(scrollState)
    ) {
        // Header con fondo púrpura y flecha de retroceso
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .background(Color(0xFF9130F2))
                .padding(start = 16.dp, end = 16.dp, top = 12.dp, bottom = 12.dp)
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(
                    imageVector = Icons.Default.ArrowBack,
                    contentDescription = "Regresar",
                    tint = Color.White,
                    modifier = Modifier.clickable { navController.popBackStack() }
                )
                Spacer(modifier = Modifier.width(16.dp))
                Text(
                    text = "Tasa de Interés",
                    fontSize = 22.sp,
                    color = Color.White,
                    fontWeight = FontWeight.Medium
                )
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Card para la definición expandible (como en la primera imagen)
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp),
            colors = CardDefaults.cardColors(containerColor = Color.White),
            shape = RoundedCornerShape(12.dp),
            elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
        ) {
            Column(modifier = Modifier.padding(16.dp)) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clickable { expanded = !expanded },
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        "¿Qué es la Tasa de Interés?",
                        fontWeight = FontWeight.Medium,
                        fontSize = 16.sp
                    )
                    Icon(
                        imageVector = if (expanded) Icons.Filled.KeyboardArrowUp else Icons.Filled.KeyboardArrowDown,
                        contentDescription = if (expanded) "Contraer" else "Expandir"
                    )
                }

                if (expanded) {
                    Spacer(modifier = Modifier.height(12.dp))
                    Text(
                        text = "La tasa de interés representa un porcentaje aplicado sobre una cantidad base para determinar el costo o ganancia de un préstamo o inversión.\n\n" +
                                "Ejemplo: Si tienes $1,000 y la tasa es 5%, el interés será:\n" +
                                "(1,000 × 5) ÷ 100 = $50",
                        fontSize = 14.sp
                    )
                }
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Sección de tipo de cálculo (similar a la primera imagen)
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp),
            colors = CardDefaults.cardColors(containerColor = Color.White),
            shape = RoundedCornerShape(12.dp),
            elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
        ) {
            Column(
                modifier = Modifier.padding(16.dp)
            ) {
                Text(
                    text = "Tipo de Cálculo",
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Medium
                )

                Spacer(modifier = Modifier.height(16.dp))

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    // Radio button para Valor Futuro
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier
                            .weight(1f)
                            .clickable { tipoCalculo = "valorFuturo" }
                    ) {
                        RadioButton(
                            selected = tipoCalculo == "valorFuturo",
                            onClick = { tipoCalculo = "valorFuturo" },
                            colors = RadioButtonDefaults.colors(
                                selectedColor = Color(0xFF9130F2)
                            )
                        )
                        Spacer(modifier = Modifier.width(4.dp))
                        Text("Valor Futuro")
                    }

                    // Radio button para Valor Presente
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier
                            .weight(1f)
                            .clickable { tipoCalculo = "valorPresente" }
                    ) {
                        RadioButton(
                            selected = tipoCalculo == "valorPresente",
                            onClick = { tipoCalculo = "valorPresente" },
                            colors = RadioButtonDefaults.colors(
                                selectedColor = Color(0xFF9130F2)
                            )
                        )
                        Spacer(modifier = Modifier.width(4.dp))
                        Text("Valor Presente")
                    }
                }
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Sección de Ingreso de datos
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp),
            colors = CardDefaults.cardColors(containerColor = Color.White),
            shape = RoundedCornerShape(12.dp),
            elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
        ) {
            Column(
                modifier = Modifier.padding(16.dp)
            ) {
                Text(
                    text = "Ingrese los datos",
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Medium
                )

                Spacer(modifier = Modifier.height(16.dp))

                // Campo para el número base
                OutlinedTextField(
                    value = numeroBase,
                    onValueChange = {
                        if (it.isEmpty() || it.matches(Regex("^\\d+(\\.\\d{0,2})?\$"))) {
                            numeroBase = it
                        }
                    },
                    label = { Text("Número base") },
                    placeholder = { Text("Número base") },
                    modifier = Modifier.fillMaxWidth(),
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedBorderColor = Color(0xFF9130F2),
                        unfocusedBorderColor = Color.Gray,
                        focusedLabelColor = Color(0xFF9130F2),
                        cursorColor = Color(0xFF9130F2)
                    ),
                    keyboardOptions = KeyboardOptions(
                        keyboardType = KeyboardType.Decimal,
                        imeAction = ImeAction.Next
                    ),
                    keyboardActions = KeyboardActions(
                        onNext = { focusManager.moveFocus(FocusDirection.Down) }
                    ),
                    leadingIcon = {
                        Text(
                            "$",
                            color = Color.Gray,
                            fontSize = 18.sp,
                            modifier = Modifier.padding(start = 12.dp)
                        )
                    },
                    singleLine = true,
                    shape = RoundedCornerShape(8.dp)
                )

                Spacer(modifier = Modifier.height(16.dp))

                // Campo para el porcentaje
                OutlinedTextField(
                    value = porcentaje,
                    onValueChange = {
                        if (it.isEmpty() || it.matches(Regex("^\\d+(\\.\\d{0,2})?\$"))) {
                            porcentaje = it
                        }
                    },
                    label = { Text("Tasa de interés (%)") },
                    placeholder = { Text("Tasa de interés (%)") },
                    modifier = Modifier.fillMaxWidth(),
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedBorderColor = Color(0xFF9130F2),
                        unfocusedBorderColor = Color.Gray,
                        focusedLabelColor = Color(0xFF9130F2),
                        cursorColor = Color(0xFF9130F2)
                    ),
                    keyboardOptions = KeyboardOptions(
                        keyboardType = KeyboardType.Decimal,
                        imeAction = ImeAction.Done
                    ),
                    keyboardActions = KeyboardActions(
                        onDone = {
                            keyboardController?.hide()
                            calcularPorcentaje(numeroBase, porcentaje)?.let {
                                resultado = it
                            }
                        }
                    ),
                    leadingIcon = {
                        Text(
                            "%",
                            color = Color.Gray,
                            fontSize = 18.sp,
                            modifier = Modifier.padding(start = 12.dp)
                        )
                    },
                    singleLine = true,
                    shape = RoundedCornerShape(8.dp)
                )

                Spacer(modifier = Modifier.height(16.dp))
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Botón de calcular
        Button(
            onClick = {
                keyboardController?.hide()
                calcularPorcentaje(numeroBase, porcentaje)?.let {
                    resultado = it
                }
            },
            modifier = Modifier
                .fillMaxWidth()
                .height(56.dp)
                .padding(horizontal = 16.dp),
            colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF9130F2)),
            shape = RoundedCornerShape(28.dp)
        ) {
            Text(
                "Calcular",
                color = Color.White,
                fontSize = 16.sp
            )
        }

        // Mostrar resultado si existe
        if (resultado != null) {
            Spacer(modifier = Modifier.height(16.dp))
            Card(
                colors = CardDefaults.cardColors(containerColor = Color.White),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp),
                shape = RoundedCornerShape(12.dp),
                elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
            ) {
                Column(
                    modifier = Modifier.padding(16.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(
                        text = "Resultado",
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Medium
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(
                        text = "$ ${String.format("%.2f", resultado ?: 0.0)}",
                        fontSize = 24.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color(0xFF9130F2)
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(
                        text = "($numeroBase × $porcentaje) ÷ 100 = $ ${String.format("%.2f", resultado ?: 0.0)}",
                        fontSize = 14.sp,
                        textAlign = TextAlign.Center
                    )
                }
            }
        }

        Spacer(modifier = Modifier.height(24.dp))
    }
}

// Función para calcular el porcentaje
private fun calcularPorcentaje(base: String, porcentaje: String): Double? {
    return try {
        val baseNum = base.toDoubleOrNull() ?: return null
        val porcentajeNum = porcentaje.toDoubleOrNull() ?: return null

        // Cálculo: (base * porcentaje) / 100
        (baseNum * porcentajeNum) / 100.0
    } catch (e: Exception) {
        null
    }
}
