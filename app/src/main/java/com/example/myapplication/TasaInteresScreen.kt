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
fun TasaInteresScreen(navController: NavHostController) {
    var numeroBase by remember { mutableStateOf("") }
    var porcentaje by remember { mutableStateOf("") }
    var resultado by remember { mutableStateOf("") }
    var expanded by remember { mutableStateOf(false) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(10.dp)
    ) {
        Spacer(modifier = Modifier.height(10.dp))

        // Botón de regresar
        Button(
            onClick = { navController.popBackStack() },
            colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF9130F2))
        ) {
            Text("< Regresar", color = Color.White)
        }

        Text(
            text = "Tasa de Interés",
            fontSize = 24.sp,
            fontWeight = FontWeight.Bold
        )

        // Tarjeta Expandible con Definición y Ejemplo
        Card(
            modifier = Modifier.fillMaxWidth(),
            colors = CardDefaults.cardColors(containerColor = Color(0xFFF5F5F5))
        ) {
            Column(modifier = Modifier.padding(12.dp)) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text("¿Qué es la Tasa de Interés?", fontWeight = FontWeight.Bold)
                    IconButton(onClick = { expanded = !expanded }) {
                        Icon(
                            imageVector = if (expanded) Icons.Filled.ExpandLess else Icons.Filled.ExpandMore,
                            contentDescription = if (expanded) "Contraer" else "Expandir"
                        )
                    }
                }
                if (expanded) {
                    Text(
                        text = "La tasa de interés representa un porcentaje aplicado sobre una cantidad base para determinar el costo o ganancia de un préstamo o inversión.\n\n" +
                                "**Ejemplo:** Si tienes $1,000 y la tasa es 5%, el interés será:\n" +
                                "(1,000 * 5) / 100 = $50",
                        fontSize = 14.sp
                    )
                }
            }
        }

        // Campo para el número base
        OutlinedTextField(
            value = numeroBase,
            onValueChange = { numeroBase = it },
            label = { Text("Número base") },
            keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Number),
            colors = OutlinedTextFieldDefaults.colors(
                focusedBorderColor = Color(0xFF9130F2),
                cursorColor = Color(0xFF9130F2)
            )
        )

        // Campo para el porcentaje
        OutlinedTextField(
            value = porcentaje,
            onValueChange = { porcentaje = it },
            label = { Text("Porcentaje (%)") },
            keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Number),
            colors = OutlinedTextFieldDefaults.colors(
                focusedBorderColor = Color(0xFF9130F2),
                cursorColor = Color(0xFF9130F2)
            )
        )

        // Botón de calcular (sin lógica, solo visual)
        Button(
            onClick = { /* Aquí se llamará al backend para calcular el porcentaje */ },
            colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF9130F2))
        ) {
            Text("Calcular Porcentaje", color = Color.White)
        }

        // Espacio reservado para mostrar el resultado
        if (resultado.isNotEmpty()) {
            Text(
                text = "Resultado: $resultado",
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold,
                color = Color(0xFF9130F2),
                modifier = Modifier.padding(top = 16.dp)
            )
        }
    }
}
