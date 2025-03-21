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
fun InteresSimpleScreen(navController: NavHostController) {
    var tasaInteres by remember { mutableStateOf("") }
    var tiempo by remember { mutableStateOf("") }
    var valorFinal by remember { mutableStateOf("") }
    var valorPresente by remember { mutableStateOf("") }
    var expanded by remember { mutableStateOf(false) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(10.dp)
    ) {
        Spacer(modifier = Modifier.height(10.dp))

        // 🔹 Botón de regresar 🔹
        Button(
            onClick = { navController.popBackStack() },
            colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF9130F2))
        ) {
            Text("< Regresar", color = Color.White)
        }

        Text(
            text = "Interés Simple",
            fontSize = 24.sp,
            fontWeight = FontWeight.Bold
        )

        // 🔽 Tarjeta Expandible con Definición y Ejemplo 🔽
        Card(
            modifier = Modifier.fillMaxWidth(),
            colors = CardDefaults.cardColors(containerColor = Color(0xFFF5F5F5))
        ) {
            Column(modifier = Modifier.padding(12.dp)) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text("¿Qué es el Interés Simple?", fontWeight = FontWeight.Bold)
                    IconButton(onClick = { expanded = !expanded }) {
                        Icon(
                            imageVector = if (expanded) Icons.Filled.ExpandLess else Icons.Filled.ExpandMore,
                            contentDescription = if (expanded) "Contraer" else "Expandir"
                        )
                    }
                }
                if (expanded) {
                    Text(
                        text = "El interés simple es el pago por el uso de dinero prestado. Se calcula multiplicando la tasa de interés por el tiempo y el valor presente.\n\n" +
                                "Fórmula:\nI = VP × i × t\n\n" +
                                "Ejemplo: Si inviertes $1,000 con una tasa del 5% anual durante 3 años:\nI = 1000 × 0.05 × 3 = $150",
                        fontSize = 14.sp
                    )
                }
            }
        }

        // 🔹 Campos de entrada reordenados 🔹
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
            value = valorFinal,
            onValueChange = { valorFinal = it },
            label = { Text("Valor Final") },
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

        // 🔹 Botón de Calcular (Sin Acción) 🔹
        Button(
            onClick = { /* Aquí no se realiza ningún cálculo aún */ },
            colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF9130F2))
        ) {
            Text("Calcular Interés", color = Color.White)
        }
    }
}

