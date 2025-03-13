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
import androidx.compose.ui.Alignment

@Composable
fun GradienteAritmeticoScreen(navController: NavHostController) {
    var primerPago by remember { mutableStateOf("") }
    var incrementoPago by remember { mutableStateOf("") }
    var tasaInteres by remember { mutableStateOf("") }
    var periodos by remember { mutableStateOf("") }
    var expanded by remember { mutableStateOf(false) }

    // Estados para los checkboxes
    var valorPresenteChecked by remember { mutableStateOf(false) }
    var valorFuturoChecked by remember { mutableStateOf(false) }

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
            text = "Gradiente Aritmético",
            fontSize = 24.sp,
            fontWeight = FontWeight.Bold
        )

        // 🔽 Tarjeta Expandible con Definición 🔽
        Card(
            modifier = Modifier.fillMaxWidth(),
            colors = CardDefaults.cardColors(containerColor = Color(0xFFF5F5F5))
        ) {
            Column(modifier = Modifier.padding(12.dp)) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text("¿Qué es el Gradiente Aritmético?", fontWeight = FontWeight.Bold)
                    IconButton(onClick = { expanded = !expanded }) {
                        Icon(
                            imageVector = if (expanded) Icons.Filled.ExpandLess else Icons.Filled.ExpandMore,
                            contentDescription = if (expanded) "Contraer" else "Expandir"
                        )
                    }
                }
                if (expanded) {
                    Text(
                        text = "El gradiente aritmético es un método utilizado para calcular el valor de una serie de pagos crecientes o decrecientes a lo largo del tiempo.\n\n" +
                                "Fórmula:\nVP = P × (1 - (1 + i)^-n) / i + G × ((1 - (1 + i)^-n) / i - n / (1 + i)^n) / i",
                        fontSize = 14.sp
                    )
                }
            }
        }

        // 🔹 Campos de entrada 🔹
        OutlinedTextField(
            value = primerPago,
            onValueChange = { primerPago = it },
            label = { Text("Primer Pago") },
            keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Number),
            colors = OutlinedTextFieldDefaults.colors(
                focusedBorderColor = Color(0xFF9130F2),
                cursorColor = Color(0xFF9130F2)
            )
        )

        OutlinedTextField(
            value = incrementoPago,
            onValueChange = { incrementoPago = it },
            label = { Text("Incremento de Pago") },
            keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Number),
            colors = OutlinedTextFieldDefaults.colors(
                focusedBorderColor = Color(0xFF9130F2),
                cursorColor = Color(0xFF9130F2)
            )
        )

        OutlinedTextField(
            value = tasaInteres,
            onValueChange = { tasaInteres = it },
            label = { Text("Tasa de Interés (%)") },
            keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Number),
            colors = OutlinedTextFieldDefaults.colors(
                focusedBorderColor = Color(0xFF9130F2),
                cursorColor = Color(0xFF9130F2)
            )
        )

        OutlinedTextField(
            value = periodos,
            onValueChange = { periodos = it },
            label = { Text("Períodos") },
            keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Number),
            colors = OutlinedTextFieldDefaults.colors(
                focusedBorderColor = Color(0xFF9130F2),
                cursorColor = Color(0xFF9130F2)
            )
        )

        // 🔹 Checkboxes alineados frente a frente 🔹
        Row(
            modifier = Modifier.fillMaxWidth().padding(start = 16.dp), // Margen a la izquierda
            verticalAlignment = Alignment.CenterVertically
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.weight(1f) // Asegura mismo ancho para ambos checkboxes
            ) {
                Checkbox(
                    checked = valorPresenteChecked,
                    onCheckedChange = {
                        valorPresenteChecked = !valorPresenteChecked
                        if (valorPresenteChecked) valorFuturoChecked = false
                    },
                    colors = CheckboxDefaults.colors(checkedColor = Color(0xFF9130F2))
                )
                Text(
                    text = "Valor Presente",
                    fontSize = 16.sp
                )
            }

            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.weight(1f) // Asegura misma distribución en la fila
            ) {
                Checkbox(
                    checked = valorFuturoChecked,
                    onCheckedChange = {
                        valorFuturoChecked = !valorFuturoChecked
                        if (valorFuturoChecked) valorPresenteChecked = false
                    },
                    colors = CheckboxDefaults.colors(checkedColor = Color(0xFF9130F2))
                )
                Text(
                    text = "Valor Futuro",
                    fontSize = 16.sp
                )
            }
        }

        // 🔹 Botón de Calcular (Sin Acción) 🔹
        Button(
            onClick = { /* Aquí no se realiza ningún cálculo aún */ },
            colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF9130F2))
        ) {
            Text("Calcular Gradiente", color = Color.White)
        }
    }
}

