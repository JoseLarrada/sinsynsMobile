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
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.AttachMoney
import androidx.compose.material.icons.filled.Calculate
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material.icons.filled.Percent
import androidx.compose.material.icons.filled.Schedule
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
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
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
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.example.myapplication.API.GradienteGeometrico.GradienteGRequest
import com.example.myapplication.viewModel.GradienteGeometricoViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun GradienteGeometricoScreen(navController: NavHostController) {
    var primerPago by remember { mutableStateOf("") }
    var tasaInteres by remember { mutableStateOf("") }
    var tasaCrecimiento by remember { mutableStateOf("") }
    var periodos by remember { mutableStateOf("") }
    var resultado by remember { mutableStateOf("") }

    var tipoOperacion by remember { mutableStateOf("Valor Presente") }
    val opciones = listOf("Valor Presente", "Valor Futuro")
    var expanded by remember { mutableStateOf(false) }

    // Nuevo selector de periodicidad
    var periodicidad by remember { mutableStateOf("Anual") }
    val opcionesPeriodicidad = listOf("Mensual", "Bimestral", "Trimestral", "Semestral", "Anual")
    var expandedPeriodicidad by remember { mutableStateOf(false) }

    val viewModel: GradienteGeometricoViewModel = viewModel()
    val valorPresente by viewModel.valorPresente.observeAsState()
    val valorFuturo by viewModel.valorFuturo.observeAsState()


    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Gradiente Geométrico") },
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
            // Explicación desplegable
            Card(
                modifier = Modifier.fillMaxWidth(),
                elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
                colors = CardDefaults.cardColors(containerColor = Color(0xFFF5F5F5))
            ) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Text(
                        "¿Qué es el Gradiente Geométrico?",
                        fontWeight = FontWeight.Bold,
                        fontSize = 16.sp,
                        color = Color(0xFF9130F2)
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(
                        text = "Es una serie de pagos que crecen a una tasa constante (g).\n\n" +
                                "Valor Presente (VP): usado para conocer cuánto vale hoy esa serie de pagos crecientes.\n" +
                                "Valor Futuro (VF): cuánto tendrás acumulado al final de los períodos.",
                        fontSize = 14.sp
                    )
                }
            }

            // Tarjeta de configuración
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

                    // Dropdown para tipo de operación
                    ExposedDropdownMenuBox(
                        expanded = expanded,
                        onExpandedChange = { expanded = !expanded }
                    ) {
                        OutlinedTextField(
                            value = tipoOperacion,
                            onValueChange = {},
                            readOnly = true,
                            label = { Text("Tipo de operación") },
                            leadingIcon = { Icon(Icons.Default.Calculate, contentDescription = null) },
                            trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = expanded) },
                            modifier = Modifier.menuAnchor().fillMaxWidth()
                        )
                        ExposedDropdownMenu(expanded = expanded, onDismissRequest = { expanded = false }) {
                            opciones.forEach { opcion ->
                                DropdownMenuItem(
                                    text = { Text(opcion) },
                                    onClick = {
                                        tipoOperacion = opcion
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
                            leadingIcon = { Icon(Icons.Default.Schedule, contentDescription = null) },
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

            // Tarjeta de datos de entrada
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

                    // Inputs
                    OutlinedTextField(
                        value = primerPago,
                        onValueChange = { primerPago = it },
                        label = { Text("Primer Pago") },
                        leadingIcon = { Icon(Icons.Default.AttachMoney, contentDescription = null) },
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                        modifier = Modifier.fillMaxWidth()
                    )

                    OutlinedTextField(
                        value = tasaInteres,
                        onValueChange = { tasaInteres = it },
                        label = { Text("Tasa de Interés (%)") },
                        leadingIcon = { Icon(Icons.Default.Percent, contentDescription = null) },
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                        modifier = Modifier.fillMaxWidth()
                    )

                    OutlinedTextField(
                        value = tasaCrecimiento,
                        onValueChange = { tasaCrecimiento = it },
                        label = { Text("Tasa de Crecimiento (%)") },
                        leadingIcon = { Icon(Icons.Default.TrendingUp, contentDescription = null) },
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                        modifier = Modifier.fillMaxWidth()
                    )

                    OutlinedTextField(
                        value = periodos,
                        onValueChange = { periodos = it },
                        label = { Text("Número de Períodos") },
                        leadingIcon = { Icon(Icons.Default.DateRange, contentDescription = null) },
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                        modifier = Modifier.fillMaxWidth()
                    )
                }
            }

            // Botón calcular
            Button(
                onClick = {
                    if (
                        primerPago.isNotEmpty() &&
                        tasaInteres.isNotEmpty() &&
                        tasaCrecimiento.isNotEmpty() &&
                        periodos.isNotEmpty()
                    ) {
                        val request = GradienteGRequest(
                            primerPago = primerPago.toDouble(),
                            tasaInteres = tasaInteres.toDouble()/100,
                            tasaCrecimiento = tasaCrecimiento.toDouble()/100,
                            periodos = periodos.toInt()
                        )

                        if (tipoOperacion == "Valor Presente") {
                            viewModel.calcularVP(request)
                        } else {
                            viewModel.calcularVF(request)
                        }
                    } else {
                        resultado = "Por favor, complete todos los campos."
                    }
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

            LaunchedEffect(valorPresente, valorFuturo) {
                valorPresente?.let {
                    if (tipoOperacion == "Valor Presente") {
                        resultado = "Valor Presente ($periodicidad): $${"%.2f".format(it)}"
                    }
                }
                valorFuturo?.let {
                    if (tipoOperacion == "Valor Futuro") {
                        resultado = "Valor Futuro ($periodicidad): $${"%.2f".format(it)}"
                    }
                }
            }

            // Mostrar resultado
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
