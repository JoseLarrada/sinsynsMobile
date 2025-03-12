package com.example.myapplication

import androidx.compose.foundation.layout.*
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
    var capital by remember { mutableStateOf("") }
    var tasa by remember { mutableStateOf("") }
    var tiempo by remember { mutableStateOf("") }
    var resultado by remember { mutableStateOf<String?>(null) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(10.dp)
    ) {
        // Botón para regresar
        Button(
            onClick = { navController.popBackStack() }, // Regresa a la pantalla anterior
            colors = ButtonDefaults.buttonColors(containerColor = Color.Gray)
        ) {
            Text("⬅ Regresar", color = Color.White)
        }

        Text(
            text = "Interés Simple",
            fontSize = 24.sp,
            fontWeight = FontWeight.Bold
        )

        Text(
            text = "El interés simple es el pago por el uso de dinero prestado. Se calcula multiplicando el capital por la tasa de interés y el tiempo.",
            fontSize = 16.sp
        )

        // Campos de entrada
        OutlinedTextField(
            value = capital,
            onValueChange = { capital = it },
            label = { Text("Capital (C)") },
            keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Number)
        )

        OutlinedTextField(
            value = tasa,
            onValueChange = { tasa = it },
            label = { Text("Tasa de interés (%)") },
            keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Number)
        )

        OutlinedTextField(
            value = tiempo,
            onValueChange = { tiempo = it },
            label = { Text("Tiempo (años)") },
            keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Number)
        )

        // Botón para calcular
        Button(
            onClick = {
                val c = capital.toDoubleOrNull()
                val i = tasa.toDoubleOrNull()?.div(100)  // Convertir porcentaje a decimal
                val t = tiempo.toDoubleOrNull()

                if (c != null && i != null && t != null) {
                    val interes = c * i * t
                    resultado = "Interés Simple: $${String.format("%.2f", interes)}"
                } else {
                    resultado = "Por favor, ingrese valores válidos."
                }
            }
        ) {
            Text("Calcular Interés")
        }

        // Mostrar resultado
        resultado?.let {
            Text(text = it, fontSize = 18.sp, fontWeight = FontWeight.Bold, color = Color.Blue)
        }
    }
}
