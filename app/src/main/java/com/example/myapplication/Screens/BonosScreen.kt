package com.example.myapplication.Screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BonosScreen(navController: NavHostController) {
    var valorNominal by remember { mutableStateOf("") }
    var tasaCupon by remember { mutableStateOf("") }
    var tasaMercado by remember { mutableStateOf("") }
    var precioMercado by remember { mutableStateOf("") }
    var años by remember { mutableStateOf("") }
    var resultado by remember { mutableStateOf("") }

    var frecuenciaSeleccionada by remember { mutableStateOf("ANUAL") }
    val frecuencias = listOf("ANUAL", "SEMESTRAL", "TRIMESTRAL", "BIMESTRAL", "MENSUAL", "DIARIA")
    var menuFrecuencia by remember { mutableStateOf(false) }

    val scrollState = rememberScrollState()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
            .verticalScroll(scrollState),
        verticalArrangement = Arrangement.spacedBy(20.dp)
    ) {
        Button(
            onClick = { navController.popBackStack() },
            colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF9130F2))
        ) {
            Text("< Regresar", color = Color.White)
        }

        Text("Cálculo de Bonos", fontSize = 24.sp, fontWeight = FontWeight.Bold, modifier = Modifier.align(
            Alignment.CenterHorizontally))

        // Inputs
        OutlinedTextField(
            value = valorNominal,
            onValueChange = { valorNominal = it },
            label = { Text("Valor Nominal") },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
            modifier = Modifier.fillMaxWidth()
        )

        OutlinedTextField(
            value = tasaCupon,
            onValueChange = { tasaCupon = it },
            label = { Text("Tasa de Cupón (%)") },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
            modifier = Modifier.fillMaxWidth()
        )

        OutlinedTextField(
            value = tasaMercado,
            onValueChange = { tasaMercado = it },
            label = { Text("Tasa de Mercado (%)") },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
            modifier = Modifier.fillMaxWidth()
        )

        OutlinedTextField(
            value = precioMercado,
            onValueChange = { precioMercado = it },
            label = { Text("Precio en el Mercado") },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
            modifier = Modifier.fillMaxWidth()
        )

        OutlinedTextField(
            value = años,
            onValueChange = { años = it },
            label = { Text("Años de duración") },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
            modifier = Modifier.fillMaxWidth()
        )

        // Frecuencia
        ExposedDropdownMenuBox(
            expanded = menuFrecuencia,
            onExpandedChange = { menuFrecuencia = !menuFrecuencia }
        ) {
            OutlinedTextField(
                value = frecuenciaSeleccionada,
                onValueChange = {},
                readOnly = true,
                label = { Text("Frecuencia") },
                trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(menuFrecuencia) },
                modifier = Modifier.menuAnchor().fillMaxWidth()
            )
            ExposedDropdownMenu(
                expanded = menuFrecuencia,
                onDismissRequest = { menuFrecuencia = false }
            ) {
                frecuencias.forEach { opcion ->
                    DropdownMenuItem(
                        text = { Text(opcion) },
                        onClick = {
                            frecuenciaSeleccionada = opcion
                            menuFrecuencia = false
                        }
                    )
                }
            }
        }

        Button(
            onClick = {
                resultado = if (
                    valorNominal.isNotEmpty() &&
                    tasaCupon.isNotEmpty() &&
                    tasaMercado.isNotEmpty() &&
                    años.isNotEmpty() &&
                    precioMercado.isNotEmpty()
                ) {
                    "Simulación: Precio, YTM, Duración y Convexidad calculados."
                } else {
                    "Completa todos los campos."
                }
            },
            modifier = Modifier.fillMaxWidth(),
            colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF9130F2))
        ) {
            Text("Calcular", color = Color.White)
        }

        if (resultado.isNotEmpty()) {
            Text(
                text = resultado,
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold,
                color = Color(0xFFF2637E),
                modifier = Modifier.align(Alignment.CenterHorizontally)
            )
        }
    }
}
