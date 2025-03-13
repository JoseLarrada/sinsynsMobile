package com.example.myapplication

import androidx.compose.foundation.layout.*
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
import androidx.compose.ui.text.input.KeyboardType


@Composable
fun InteresCompuestoScreen(navController: NavHostController) {
    var tasaInteres by remember { mutableStateOf("") }
    var tiempo by remember { mutableStateOf("") }
    var valorFuturo by remember { mutableStateOf("") }
    var valorPresente by remember { mutableStateOf("") }
    var expanded by remember { mutableStateOf(false) } // Estado para la tarjeta desplegable

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(10.dp)
    ) {
        Spacer(modifier = Modifier.height(10.dp)) // Mueve el botón de regresar más abajo

        // 🔹 Botón de regresar
        Button(
            onClick = { navController.popBackStack() },
            colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF9130F2))
        ) {
            Text("< Regresar", color = Color.White)
        }

        // 🔹 Título
        Text(
            text = "Interés Compuesto",
            fontSize = 24.sp,
            fontWeight = FontWeight.Bold
        )

        // 🔽 Tarjeta Expandible con Definición y Fórmula 🔽
        Card(
            modifier = Modifier.fillMaxWidth(),
            colors = CardDefaults.cardColors(containerColor = Color(0xFFF5F5F5))
        ) {
            Column(modifier = Modifier.padding(12.dp)) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text("¿Qué es el Interés Compuesto?", fontWeight = FontWeight.Bold)
                    IconButton(onClick = { expanded = !expanded }) {
                        Icon(
                            imageVector = if (expanded) Icons.Filled.ExpandLess else Icons.Filled.ExpandMore,
                            contentDescription = if (expanded) "Contraer" else "Expandir"
                        )
                    }
                }
                if (expanded) {
                    Text(
                        text = "El interés compuesto es la acumulación de intereses que generan más intereses en cada período. Se calcula con la fórmula:\n\n" +
                                "VF = VP (1 + i)^n\n\n" +
                                "Donde:\n" +
                                "• VF = Valor Futuro\n" +
                                "• VP = Valor Presente\n" +
                                "• i = Tasa de interés\n" +
                                "• n = Número de periodos\n\n" +
                                "Ejemplo: Si inviertes $1,000 con una tasa del 5% anual durante 3 años:\n" +
                                "VF = 1000 × (1 + 0.05)^3 = $1,157.63",
                        fontSize = 14.sp
                    )
                }
            }
        }

        // 🔹 Campos de entrada
        OutlinedTextField(
            value = tasaInteres,
            onValueChange = { tasaInteres = it },
            label = { Text("Tasa de interés (%)") },
            keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Number),
            colors = OutlinedTextFieldDefaults.colors(
                focusedBorderColor = Color(0xFF9130F2),
                cursorColor = Color(0xFF9130F2)
            )
        )

        OutlinedTextField(
            value = tiempo,
            onValueChange = { tiempo = it },
            label = { Text("Tiempo (años)") },
            keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Number),
            colors = OutlinedTextFieldDefaults.colors(
                focusedBorderColor = Color(0xFF9130F2),
                cursorColor = Color(0xFF9130F2)
            )
        )

        OutlinedTextField(
            value = valorFuturo,
            onValueChange = { valorFuturo = it },
            label = { Text("Valor Futuro") },
            keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Number),
            colors = OutlinedTextFieldDefaults.colors(
                focusedBorderColor = Color(0xFF9130F2),
                cursorColor = Color(0xFF9130F2)
            )
        )

        OutlinedTextField(
            value = valorPresente,
            onValueChange = { valorPresente = it },
            label = { Text("Valor Presente") },
            keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Number),
            colors = OutlinedTextFieldDefaults.colors(
                focusedBorderColor = Color(0xFF9130F2),
                cursorColor = Color(0xFF9130F2)
            )
        )

        // 🔹 Botón (sin cálculos por ahora)
        Button(
            onClick = { /* Implementar cálculos después */ },
            colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF9130F2))
        ) {
            Text("Calcular Interés", color = Color.White)
        }
    }
}
