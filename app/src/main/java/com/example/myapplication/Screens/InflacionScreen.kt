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
fun InflacionScreen(navController: NavHostController) {
    var tipoSeleccionado by remember { mutableStateOf("Tasa real") }
    val tipos = listOf("Tasa real", "Tasa nominal", "Valor futuro", "Valor presente")
    var menuExpanded by remember { mutableStateOf(false) }

    var tasaNominal by remember { mutableStateOf("") }
    var tasaReal by remember { mutableStateOf("") }
    var tasaInflacion by remember { mutableStateOf("") }
    var valor by remember { mutableStateOf("") }
    var periodos by remember { mutableStateOf("") }
    var resultado by remember { mutableStateOf("") }

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

        Text("Cálculo de Inflación", fontSize = 24.sp, fontWeight = FontWeight.Bold, modifier = Modifier.align(
            Alignment.CenterHorizontally))

        // Selector de tipo
        ExposedDropdownMenuBox(
            expanded = menuExpanded,
            onExpandedChange = { menuExpanded = !menuExpanded }
        ) {
            OutlinedTextField(
                value = tipoSeleccionado,
                onValueChange = {},
                readOnly = true,
                label = { Text("Tipo de cálculo") },
                trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(menuExpanded) },
                modifier = Modifier.menuAnchor().fillMaxWidth()
            )
            ExposedDropdownMenu(
                expanded = menuExpanded,
                onDismissRequest = { menuExpanded = false }
            ) {
                tipos.forEach { opcion ->
                    DropdownMenuItem(
                        text = { Text(opcion) },
                        onClick = {
                            tipoSeleccionado = opcion
                            menuExpanded = false
                        }
                    )
                }
            }
        }

        // Campos comunes
        OutlinedTextField(
            value = tasaNominal,
            onValueChange = { tasaNominal = it },
            label = { Text("Tasa Nominal (%)") },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
            modifier = Modifier.fillMaxWidth()
        )

        OutlinedTextField(
            value = tasaReal,
            onValueChange = { tasaReal = it },
            label = { Text("Tasa Real (%)") },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
            modifier = Modifier.fillMaxWidth()
        )

        OutlinedTextField(
            value = tasaInflacion,
            onValueChange = { tasaInflacion = it },
            label = { Text("Tasa de Inflación (%)") },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
            modifier = Modifier.fillMaxWidth()
        )

        OutlinedTextField(
            value = valor,
            onValueChange = { valor = it },
            label = { Text("Valor") },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
            modifier = Modifier.fillMaxWidth()
        )

        OutlinedTextField(
            value = periodos,
            onValueChange = { periodos = it },
            label = { Text("Períodos") },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
            modifier = Modifier.fillMaxWidth()
        )

        // Botón calcular
        Button(
            onClick = {
                resultado = if (
                    tasaNominal.isNotEmpty() ||
                    tasaReal.isNotEmpty() ||
                    tasaInflacion.isNotEmpty() ||
                    valor.isNotEmpty()
                ) {
                    "Resultado estimado para $tipoSeleccionado: \$XXX"
                } else {
                    "Por favor, completa al menos un campo necesario."
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
