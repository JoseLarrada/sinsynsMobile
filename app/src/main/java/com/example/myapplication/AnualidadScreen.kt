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
fun AnualidadesScreen(navController: NavHostController) {
    var tasaAnualidad by remember { mutableStateOf("") }
    var periodoPago by remember { mutableStateOf("") }
    var anualidad by remember { mutableStateOf("") }
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
            text = "Anualidades",
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
                    Text("¿Qué son las Anualidades?", fontWeight = FontWeight.Bold)
                    IconButton(onClick = { expanded = !expanded }) {
                        Icon(
                            imageVector = if (expanded) Icons.Filled.ExpandLess else Icons.Filled.ExpandMore,
                            contentDescription = if (expanded) "Contraer" else "Expandir"
                        )
                    }
                }
                if (expanded) {
                    Text(
                        text = "Las anualidades son una serie de pagos iguales que se realizan en intervalos regulares de tiempo. Pueden ser **ordinarias** (al final de cada período) o **anticipadas** (al inicio de cada período).\n\n" +
                                "**Fórmula para Anualidades Ordinarias:**\n" +
                                "A = VF * (i / (1 - (1 + i)^-n))\n\n" +
                                "**Ejemplo:** Si deseas recibir $10,000 al final de cada año durante 5 años con una tasa del 6%:\n" +
                                "A = 10,000 * (0.06 / (1 - (1.06)^-5)) ≈ $2,374.11",
                        fontSize = 14.sp
                    )
                }
            }
        }

        // 🔹 Campos de entrada reordenados 🔹
        OutlinedTextField(
            value = tasaAnualidad,
            onValueChange = { tasaAnualidad = it },
            label = { Text("Tasa de Anualidad (%)") },
            keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Number),
            colors = OutlinedTextFieldDefaults.colors(
                focusedBorderColor = Color(0xFF9130F2),
                cursorColor = Color(0xFF9130F2)
            )
        )

        OutlinedTextField(
            value = periodoPago,
            onValueChange = { periodoPago = it },
            label = { Text("Período de Pago (años)") },
            keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Number),
            colors = OutlinedTextFieldDefaults.colors(
                focusedBorderColor = Color(0xFF9130F2),
                cursorColor = Color(0xFF9130F2)
            )
        )

        OutlinedTextField(
            value = anualidad,
            onValueChange = { anualidad = it },
            label = { Text("Anualidad") },
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
            Text("Calcular Anualidad", color = Color.White)
        }
    }
}
