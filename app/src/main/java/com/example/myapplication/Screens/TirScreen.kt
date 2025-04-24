package com.example.myapplication.Screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountBalance
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.AttachMoney
import androidx.compose.material.icons.filled.BarChart
import androidx.compose.material.icons.filled.Calculate
import androidx.compose.material.icons.filled.Percent
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material.icons.filled.Schedule
import androidx.compose.material.icons.filled.TrendingDown
import androidx.compose.material.icons.filled.TrendingUp
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
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
fun TirScreen(navController: NavHostController) {
    var tipoTir by remember { mutableStateOf("TIR Simple") }
    val tipos = listOf("TIR Simple", "TIR Real", "TIR Modificada", "TIR Contable")
    var resultado by remember { mutableStateOf("") }

    // Nuevo selector de periodicidad
    var periodicidad by remember { mutableStateOf("Anual") }
    val opcionesPeriodicidad = listOf("Mensual", "Bimestral", "Trimestral", "Semestral", "Anual")
    var expandedPeriodicidad by remember { mutableStateOf(false) }

    // Estados para los inputs según el tipo
    var flujos by remember { mutableStateOf("") }
    var inflacion by remember { mutableStateOf("") }
    var utilidad by remember { mutableStateOf("") }
    var inversionInicial by remember { mutableStateOf("") }
    var tasaFinanciamiento by remember { mutableStateOf("") }
    var tasaReinversion by remember { mutableStateOf("") }
    var periodos by remember { mutableStateOf("") }
    var flujosPositivos by remember { mutableStateOf("") }
    var flujosNegativos by remember { mutableStateOf("") }
    var expanded by remember { mutableStateOf(false) }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Cálculo de TIR") },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(
                            imageVector = Icons.Default.ArrowBack,
                            contentDescription = "Regresar",
                            tint = Color.White
                        )
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = Color(0xFF9130F2),
                    titleContentColor = Color.White
                )
            )
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(horizontal = 16.dp, vertical = 12.dp)
                .verticalScroll(rememberScrollState()),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            Card(
                modifier = Modifier.fillMaxWidth(),
                elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
                colors = CardDefaults.cardColors(containerColor = Color.White)
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp),
                    verticalArrangement = Arrangement.spacedBy(16.dp)
                ) {
                    Text(
                        "Configuración",
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color(0xFF9130F2)
                    )

                    // Dropdown para tipo de TIR
                    ExposedDropdownMenuBox(
                        expanded = expanded,
                        onExpandedChange = { expanded = !expanded }
                    ) {
                        OutlinedTextField(
                            value = tipoTir,
                            onValueChange = {},
                            readOnly = true,
                            label = { Text("Tipo de TIR") },
                            trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = expanded) },
                            modifier = Modifier.menuAnchor().fillMaxWidth()
                        )
                        ExposedDropdownMenu(expanded = expanded, onDismissRequest = { expanded = false }) {
                            tipos.forEach { tipo ->
                                DropdownMenuItem(
                                    text = { Text(tipo) },
                                    onClick = {
                                        tipoTir = tipo
                                        expanded = false
                                    }
                                )
                            }
                        }
                    }

                    // Dropdown para periodicidad
                    ExposedDropdownMenuBox(
                        expanded = expandedPeriodicidad,
                        onExpandedChange = { expandedPeriodicidad = !expandedPeriodicidad }
                    ) {
                        OutlinedTextField(
                            value = periodicidad,
                            onValueChange = {},
                            readOnly = true,
                            label = { Text("Periodicidad") },
                            trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = expandedPeriodicidad) },
                            modifier = Modifier.menuAnchor().fillMaxWidth()
                        )
                        ExposedDropdownMenu(
                            expanded = expandedPeriodicidad,
                            onDismissRequest = { expandedPeriodicidad = false }
                        ) {
                            opcionesPeriodicidad.forEach { opcion ->
                                DropdownMenuItem(
                                    text = { Text(opcion) },
                                    onClick = {
                                        periodicidad = opcion
                                        expandedPeriodicidad = false
                                    }
                                )
                            }
                        }
                    }
                }
            }

            Card(
                modifier = Modifier.fillMaxWidth(),
                elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
                colors = CardDefaults.cardColors(containerColor = Color.White)
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp),
                    verticalArrangement = Arrangement.spacedBy(16.dp)
                ) {
                    Text(
                        "Datos de entrada",
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color(0xFF9130F2)
                    )

                    // Inputs según el tipo de TIR seleccionado
                    when (tipoTir) {
                        "TIR Simple" -> {
                            OutlinedTextField(
                                value = flujos,
                                onValueChange = { flujos = it },
                                label = { Text("Flujos (separados por coma)") },
                                modifier = Modifier.fillMaxWidth(),
                                leadingIcon = { Icon(Icons.Default.AttachMoney, contentDescription = null) }
                            )
                        }

                        "TIR Real" -> {
                            OutlinedTextField(
                                value = inflacion,
                                onValueChange = { inflacion = it },
                                label = { Text("Tasa de inflación (%)") },
                                modifier = Modifier.fillMaxWidth(),
                                leadingIcon = { Icon(Icons.Default.Percent, contentDescription = null) },
                                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
                            )
                            OutlinedTextField(
                                value = flujos,
                                onValueChange = { flujos = it },
                                label = { Text("Flujos (separados por coma)") },
                                modifier = Modifier.fillMaxWidth(),
                                leadingIcon = { Icon(Icons.Default.AttachMoney, contentDescription = null) }
                            )
                        }

                        "TIR Modificada" -> {
                            OutlinedTextField(
                                value = flujosPositivos,
                                onValueChange = { flujosPositivos = it },
                                label = { Text("Flujos positivos (monto;periodo)") },
                                modifier = Modifier.fillMaxWidth(),
                                leadingIcon = { Icon(Icons.Default.TrendingUp, contentDescription = null) }
                            )
                            OutlinedTextField(
                                value = flujosNegativos,
                                onValueChange = { flujosNegativos = it },
                                label = { Text("Flujos negativos (monto;periodo)") },
                                modifier = Modifier.fillMaxWidth(),
                                leadingIcon = { Icon(Icons.Default.TrendingDown, contentDescription = null) }
                            )
                            OutlinedTextField(
                                value = tasaReinversion,
                                onValueChange = { tasaReinversion = it },
                                label = { Text("Tasa de reinversión (%)") },
                                modifier = Modifier.fillMaxWidth(),
                                leadingIcon = { Icon(Icons.Default.Refresh, contentDescription = null) },
                                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
                            )
                            OutlinedTextField(
                                value = tasaFinanciamiento,
                                onValueChange = { tasaFinanciamiento = it },
                                label = { Text("Tasa de financiamiento (%)") },
                                modifier = Modifier.fillMaxWidth(),
                                leadingIcon = { Icon(Icons.Default.AccountBalance, contentDescription = null) },
                                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
                            )
                            OutlinedTextField(
                                value = periodos,
                                onValueChange = { periodos = it },
                                label = { Text("Número de períodos") },
                                modifier = Modifier.fillMaxWidth(),
                                leadingIcon = { Icon(Icons.Default.Schedule, contentDescription = null) },
                                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
                            )
                        }

                        "TIR Contable" -> {
                            OutlinedTextField(
                                value = utilidad,
                                onValueChange = { utilidad = it },
                                label = { Text("Utilidad promedio anual") },
                                modifier = Modifier.fillMaxWidth(),
                                leadingIcon = { Icon(Icons.Default.BarChart, contentDescription = null) },
                                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
                            )
                            OutlinedTextField(
                                value = inversionInicial,
                                onValueChange = { inversionInicial = it },
                                label = { Text("Inversión inicial") },
                                modifier = Modifier.fillMaxWidth(),
                                leadingIcon = { Icon(Icons.Default.AttachMoney, contentDescription = null) },
                                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
                            )
                        }
                    }
                }
            }

            // Botón calcular
            Button(
                onClick = {
                    resultado = "TIR $tipoTir ($periodicidad): 12.3%" // Aquí luego llamas al ViewModel/API
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(56.dp),
                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF9130F2)),
                shape = RoundedCornerShape(12.dp)
            ) {
                Icon(
                    imageVector = Icons.Default.Calculate,
                    contentDescription = null,
                    tint = Color.White,
                    modifier = Modifier.padding(end = 8.dp)
                )
                Text("Calcular", color = Color.White, fontSize = 16.sp)
            }

            if (resultado.isNotEmpty()) {
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
                    colors = CardDefaults.cardColors(containerColor = Color(0xFFF2F2F2))
                ) {
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(16.dp),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Text(
                            text = "Resultado",
                            fontSize = 16.sp,
                            fontWeight = FontWeight.Bold,
                            color = Color(0xFF9130F2)
                        )
                        Spacer(modifier = Modifier.height(8.dp))
                        Text(
                            text = resultado,
                            fontSize = 24.sp,
                            fontWeight = FontWeight.Bold,
                            color = Color(0xFFF2637E)
                        )
                    }
                }
            }
        }
    }
}
