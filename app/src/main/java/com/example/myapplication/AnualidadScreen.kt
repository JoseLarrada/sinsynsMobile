package com.example.myapplication

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ExpandLess
import androidx.compose.material.icons.filled.ExpandMore
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import androidx.compose.foundation.text.KeyboardOptions

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AnualidadesScreen(navController: NavHostController) {
    var tasaInteres by remember { mutableStateOf("") }
    var numeroPeriodos by remember { mutableStateOf("") }
    var valorAnualidad by remember { mutableStateOf("") }
    var resultado by remember { mutableStateOf("") }
    var expanded by remember { mutableStateOf(false) }
    var expandedMenu by remember { mutableStateOf(false) }
    var periodicidad by remember { mutableStateOf("Anual") }

    val opcionesPeriodicidad = listOf("Mensual", "Bimestral", "Trimestral", "Semestral", "Anual")
    val scrollState = rememberScrollState()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
            .verticalScroll(scrollState),
        verticalArrangement = Arrangement.spacedBy(25.dp)
    ) {
        Button(
            onClick = { navController.popBackStack() },
            colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF9130F2))
        ) {
            Text("< Regresar", color = Color.White)
        }

        Text(
            text = "Cálculo de Anualidades",
            fontSize = 24.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.align(Alignment.CenterHorizontally)
        )

        Card(
            modifier = Modifier.fillMaxWidth(),
            colors = CardDefaults.cardColors(containerColor = Color(0xFFF5F5F5))
        ) {
            Column(modifier = Modifier.padding(12.dp)) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text("¿Qué son las Anualidades?", fontWeight = FontWeight.Bold)
                    IconButton(onClick = { expanded = !expanded }) {
                        Icon(
                            imageVector = if (expanded) Icons.Default.ExpandLess else Icons.Default.ExpandMore,
                            contentDescription = null
                        )
                    }
                }
                if (expanded) {
                    Text(
                        text = "Las anualidades son una serie de pagos iguales que se realizan en intervalos regulares de tiempo. " +
                                "Pueden ser ordinarias (al final del período) o anticipadas (al inicio del período).\n\n" +
                                "Fórmula para el Valor Futuro:\nVF = A * ((1 + i)^n - 1) / i\n\n" +
                                "Fórmula para el Valor Presente:\nVP = A * (1 - (1 + i)^-n) / i",
                        fontSize = 14.sp
                    )
                }
            }
        }

        if (!expanded) {
            val labelStyle = Modifier.weight(1f)

            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                OutlinedTextField(
                    value = valorAnualidad,
                    onValueChange = { valorAnualidad = it },
                    label = { Text("Anualidad") },
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedBorderColor = Color(0xFF9130F2),
                        cursorColor = Color(0xFF9130F2)
                    ),
                    modifier = labelStyle
                )

                OutlinedTextField(
                    value = tasaInteres,
                    onValueChange = { tasaInteres = it },
                    label = { Text("Tasa (%)") },
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedBorderColor = Color(0xFF9130F2),
                        cursorColor = Color(0xFF9130F2)
                    ),
                    modifier = labelStyle
                )
            }

            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                ExposedDropdownMenuBox(
                    expanded = expandedMenu,
                    onExpandedChange = { expandedMenu = !expandedMenu },
                    modifier = labelStyle
                ) {
                    OutlinedTextField(
                        value = periodicidad,
                        onValueChange = {},
                        readOnly = true,
                        label = { Text("Periodicidad") },
                        trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = expandedMenu) },
                        colors = OutlinedTextFieldDefaults.colors(
                            focusedBorderColor = Color(0xFF9130F2),
                            cursorColor = Color(0xFF9130F2)
                        ),
                        modifier = Modifier.menuAnchor().fillMaxWidth()
                    )
                    ExposedDropdownMenu(
                        expanded = expandedMenu,
                        onDismissRequest = { expandedMenu = false }
                    ) {
                        opcionesPeriodicidad.forEach { opcion ->
                            DropdownMenuItem(
                                text = { Text(opcion) },
                                onClick = {
                                    periodicidad = opcion
                                    expandedMenu = false
                                }
                            )
                        }
                    }
                }

                OutlinedTextField(
                    value = numeroPeriodos,
                    onValueChange = { numeroPeriodos = it },
                    label = { Text("Períodos") },
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedBorderColor = Color(0xFF9130F2),
                        cursorColor = Color(0xFF9130F2)
                    ),
                    modifier = labelStyle
                )
            }

            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.Center) {
                Button(
                    onClick = {
                        resultado = if (
                            tasaInteres.isNotEmpty() &&
                            numeroPeriodos.isNotEmpty() &&
                            valorAnualidad.isNotEmpty()
                        ) {
                            "Resultado calculado (simulado): \$XXXX (\$periodicidad)"
                        } else {
                            "Por favor, complete todos los campos."
                        }
                    },
                    colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF9130F2))
                ) {
                    Text("Calcular", color = Color.White)
                }
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
}
