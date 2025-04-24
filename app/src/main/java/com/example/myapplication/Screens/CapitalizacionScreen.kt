package com.example.myapplication.Screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
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
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccessTime
import androidx.compose.material.icons.filled.AccountBalance
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.AttachMoney
import androidx.compose.material.icons.filled.Calculate
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material.icons.filled.People
import androidx.compose.material.icons.filled.Percent
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material.icons.filled.Schedule
import androidx.compose.material.icons.filled.Security
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Switch
import androidx.compose.material3.SwitchDefaults
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.ui.Alignment
import androidx.compose.ui.text.input.KeyboardType
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.myapplication.API.Capitalizaciones.AporteDTO
import com.example.myapplication.API.Capitalizaciones.CapitalizacionColectivaRequest
import com.example.myapplication.API.Capitalizaciones.CapitalizacionIndividualRequest
import com.example.myapplication.API.Capitalizaciones.CapitalizacionSegurosRequest
import com.example.myapplication.viewModel.CapitalizacionViewModel


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CapitalizacionScreen(navController: NavHostController) {
    var tipoSeleccionado by remember { mutableStateOf("Colectiva") }
    val tipos = listOf("Colectiva", "Individual", "Mixto", "Seguros")
    var dropdownExpanded by remember { mutableStateOf(false) }

    // Periodicidad común para todos los tipos
    var periodicidad by remember { mutableStateOf("Anual") }
    val opcionesPeriodicidad = listOf("Mensual", "Bimestral", "Trimestral", "Semestral", "Anual")
    var expandedPeriodicidad by remember { mutableStateOf(false) }

    val viewModel: CapitalizacionViewModel = viewModel()
    val resultadoAPI by viewModel.resultado.observeAsState()
    val resultadoSegurosAPI by viewModel.resultadoSeguros.observeAsState()

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Sistemas de Capitalización") },
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

                    // Dropdown para tipo de capitalización
                    ExposedDropdownMenuBox(
                        expanded = dropdownExpanded,
                        onExpandedChange = { dropdownExpanded = !dropdownExpanded }
                    ) {
                        OutlinedTextField(
                            value = tipoSeleccionado,
                            onValueChange = {},
                            readOnly = true,
                            label = { Text("Tipo de Capitalización") },
                            leadingIcon = { Icon(Icons.Default.AccountBalance, contentDescription = null) },
                            trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = dropdownExpanded) },
                            modifier = Modifier.menuAnchor().fillMaxWidth()
                        )
                        ExposedDropdownMenu(
                            expanded = dropdownExpanded,
                            onDismissRequest = { dropdownExpanded = false }
                        ) {
                            tipos.forEach { tipo ->
                                DropdownMenuItem(
                                    text = { Text(tipo) },
                                    onClick = {
                                        tipoSeleccionado = tipo
                                        dropdownExpanded = false
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
                    // Contenido según el tipo seleccionado
                    when (tipoSeleccionado) {
                        "Colectiva" -> CapitalizacionColectivaUI(periodicidad)
                        "Individual" -> CapitalizacionIndividualUI(periodicidad)
                        "Mixto" -> CapitalizacionMixtoUI(periodicidad)
                        "Seguros" -> CapitalizacionSegurosUI(periodicidad)
                    }
                }
            }
        }
    }
}

@Composable
fun CapitalizacionColectivaUI(periodicidad: String) {
    var tasa by remember { mutableStateOf("") }
    val aportes = remember { mutableStateListOf(AporteUI()) }
    val viewModel: CapitalizacionViewModel = viewModel()
    val resultadoAPI by viewModel.resultado.observeAsState()

    Column(verticalArrangement = Arrangement.spacedBy(16.dp)) {
        Text(
            "Capitalización Colectiva",
            fontSize = 18.sp,
            fontWeight = FontWeight.Bold,
            color = Color(0xFF9130F2)
        )

        Text(
            "En este sistema, los aportes de todos los miembros se unifican para generar un capital común.",
            fontSize = 14.sp,
            color = Color.Gray
        )

        aportes.forEachIndexed { index, aporte ->
            Card(
                modifier = Modifier.fillMaxWidth(),
                colors = CardDefaults.cardColors(containerColor = Color(0xFFF5F5F5))
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(12.dp),
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    Text("Aporte ${index + 1}", fontWeight = FontWeight.Bold)

                    OutlinedTextField(
                        value = aporte.monto,
                        onValueChange = { aportes[index] = aporte.copy(monto = it) },
                        label = { Text("Monto") },
                        leadingIcon = { Icon(Icons.Default.AttachMoney, contentDescription = null) },
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                        modifier = Modifier.fillMaxWidth()
                    )

                    OutlinedTextField(
                        value = aporte.tiempo,
                        onValueChange = { aportes[index] = aporte.copy(tiempo = it) },
                        label = { Text("Tiempo (períodos)") },
                        leadingIcon = { Icon(Icons.Default.AccessTime, contentDescription = null) },
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                        modifier = Modifier.fillMaxWidth()
                    )
                }
            }

            Spacer(modifier = Modifier.height(8.dp))
        }

        Button(
            onClick = { aportes.add(AporteUI()) },
            colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF4CAF50)),
            modifier = Modifier.fillMaxWidth()
        ) {
            Icon(
                imageVector = Icons.Default.Add,
                contentDescription = null,
                tint = Color.White,
                modifier = Modifier.padding(end = 8.dp)
            )
            Text("Agregar Aporte", color = Color.White)
        }

        OutlinedTextField(
            value = tasa,
            onValueChange = { tasa = it },
            label = { Text("Tasa (%)") },
            leadingIcon = { Icon(Icons.Default.Percent, contentDescription = null) },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
            modifier = Modifier.fillMaxWidth()
        )

        Button(
            onClick = {
                val tasaDouble = tasa.toDoubleOrNull()?.div(100)
                val aportesValidos = aportes.mapNotNull {
                    val monto = it.monto.toDoubleOrNull()
                    val tiempo = it.tiempo.toDoubleOrNull()
                    if (monto != null && tiempo != null) AporteDTO(monto, tiempo) else null
                }

                if (tasaDouble != null && aportesValidos.isNotEmpty()) {
                    viewModel.calcularColectiva(CapitalizacionColectivaRequest(aportesValidos, tasaDouble))
                }
            },
            colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF9130F2)),
            modifier = Modifier
                .fillMaxWidth()
                .height(56.dp),
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

        if (resultadoAPI!=null) {
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
                        text = "Capital acumulado: $${String.format("%.2f", resultadoAPI)}",
                        fontSize = 20.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color(0xFFF2637E)
                    )
                }
            }
        }
    }
}

data class AporteUI(val monto: String = "", val tiempo: String = "")

@Composable
fun CapitalizacionIndividualUI(periodicidad: String) {
    var aporte by remember { mutableStateOf("") }
    var tasa by remember { mutableStateOf("") }
    var numAnios by remember { mutableStateOf("") }
    var capitalizaciones by remember { mutableStateOf("") }
    val viewModel: CapitalizacionViewModel = viewModel()
    val resultadoAPI by viewModel.resultado.observeAsState()

    Column(verticalArrangement = Arrangement.spacedBy(16.dp)) {
        Text(
            "Capitalización Individual",
            fontSize = 18.sp,
            fontWeight = FontWeight.Bold,
            color = Color(0xFF9130F2)
        )

        Text(
            "En este sistema, cada persona realiza aportes personales para generar su propio capital.",
            fontSize = 14.sp,
            color = Color.Gray
        )

        OutlinedTextField(
            value = aporte,
            onValueChange = { aporte = it },
            label = { Text("Aporte") },
            leadingIcon = { Icon(Icons.Default.AttachMoney, contentDescription = null) },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
            modifier = Modifier.fillMaxWidth()
        )

        OutlinedTextField(
            value = tasa,
            onValueChange = { tasa = it },
            label = { Text("Tasa (%)") },
            leadingIcon = { Icon(Icons.Default.Percent, contentDescription = null) },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
            modifier = Modifier.fillMaxWidth()
        )

        OutlinedTextField(
            value = numAnios,
            onValueChange = { numAnios = it },
            label = { Text("Número de Años") },
            leadingIcon = { Icon(Icons.Default.DateRange, contentDescription = null) },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
            modifier = Modifier.fillMaxWidth()
        )

        OutlinedTextField(
            value = capitalizaciones,
            onValueChange = { capitalizaciones = it },
            label = { Text("Capitalizaciones por año") },
            leadingIcon = { Icon(Icons.Default.Refresh, contentDescription = null) },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
            modifier = Modifier.fillMaxWidth()
        )

        Button(
            onClick = {
                val aporteDouble = aporte.toDoubleOrNull()
                val tasaDouble = tasa.toDoubleOrNull()?.div(100)
                val aniosInt = numAnios.toIntOrNull()
                val capsPorAnio = capitalizaciones.toIntOrNull()

                if (aporteDouble != null && tasaDouble != null && aniosInt != null && capsPorAnio != null) {
                    viewModel.calcularIndividual(
                        CapitalizacionIndividualRequest(
                            aporte = aporteDouble,
                            tasa = tasaDouble,
                            numAnios = aniosInt,
                            capitalizaciones = capsPorAnio
                        )
                    )
                }
            },
            colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF9130F2)),
            modifier = Modifier
                .fillMaxWidth()
                .height(56.dp),
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

        if (resultadoAPI !=null) {
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
                        text = "Capital acumulado: $${String.format("%.2f", resultadoAPI)}",
                        fontSize = 20.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color(0xFFF2637E)
                    )
                }
            }
        }
    }
}

@Composable
fun CapitalizacionMixtoUI(periodicidad: String) {
    var aporteIndMonto by remember { mutableStateOf("") }
    var aporteIndTiempo by remember { mutableStateOf("") }
    var porcentajeIndividual by remember { mutableStateOf("") }
    var aporte by remember { mutableStateOf("") }
    var tasa by remember { mutableStateOf("") }
    var numAnios by remember { mutableStateOf("") }
    var capitalizaciones by remember { mutableStateOf("") }
    val viewModel: CapitalizacionViewModel = viewModel()
    val resultadoAPI by viewModel.resultado.observeAsState()

    val aportesColectivos = remember { mutableStateListOf(AporteUI()) }
    var tasaColectiva by remember { mutableStateOf("") }

    Column(verticalArrangement = Arrangement.spacedBy(16.dp)) {
        Text(
            "Capitalización Mixta",
            fontSize = 18.sp,
            fontWeight = FontWeight.Bold,
            color = Color(0xFF9130F2)
        )

        Text(
            "Este sistema combina la capitalización individual con la colectiva para obtener beneficios de ambos.",
            fontSize = 14.sp,
            color = Color.Gray
        )

        Card(
            modifier = Modifier.fillMaxWidth(),
            colors = CardDefaults.cardColors(containerColor = Color(0xFFF5F5F5))
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Icon(
                        Icons.Default.Person,
                        contentDescription = null,
                        tint = Color(0xFF9130F2),
                        modifier = Modifier.size(24.dp)
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(
                        "Aporte Individual",
                        fontWeight = FontWeight.Bold,
                        color = Color(0xFF9130F2)
                    )
                }

                OutlinedTextField(
                    value = aporteIndMonto,
                    onValueChange = { aporteIndMonto = it },
                    label = { Text("Monto") },
                    leadingIcon = { Icon(Icons.Default.AttachMoney, contentDescription = null) },
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                    modifier = Modifier.fillMaxWidth()
                )

                OutlinedTextField(
                    value = aporteIndTiempo,
                    onValueChange = { aporteIndTiempo = it },
                    label = { Text("Tiempo (períodos)") },
                    leadingIcon = { Icon(Icons.Default.AccessTime, contentDescription = null) },
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                    modifier = Modifier.fillMaxWidth()
                )

                OutlinedTextField(
                    value = porcentajeIndividual,
                    onValueChange = { porcentajeIndividual = it },
                    label = { Text("Porcentaje Individual (%)") },
                    leadingIcon = { Icon(Icons.Default.Percent, contentDescription = null) },
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                    modifier = Modifier.fillMaxWidth()
                )
            }
        }

        Card(
            modifier = Modifier.fillMaxWidth(),
            colors = CardDefaults.cardColors(containerColor = Color(0xFFF5F5F5))
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Icon(
                        Icons.Default.People,
                        contentDescription = null,
                        tint = Color(0xFF9130F2),
                        modifier = Modifier.size(24.dp)
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(
                        "Aportes Colectivos",
                        fontWeight = FontWeight.Bold,
                        color = Color(0xFF9130F2)
                    )
                }

                aportesColectivos.forEachIndexed { index, aporte ->
                    Card(
                        modifier = Modifier.fillMaxWidth(),
                        colors = CardDefaults.cardColors(containerColor = Color.White)
                    ) {
                        Column(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(12.dp),
                            verticalArrangement = Arrangement.spacedBy(8.dp)
                        ) {
                            Text("Aporte Colectivo ${index + 1}", fontWeight = FontWeight.Medium)

                            OutlinedTextField(
                                value = aporte.monto,
                                onValueChange = { aportesColectivos[index] = aporte.copy(monto = it) },
                                label = { Text("Monto") },
                                leadingIcon = { Icon(Icons.Default.AttachMoney, contentDescription = null) },
                                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                                modifier = Modifier.fillMaxWidth()
                            )

                            OutlinedTextField(
                                value = aporte.tiempo,
                                onValueChange = { aportesColectivos[index] = aporte.copy(tiempo = it) },
                                label = { Text("Tiempo (períodos)") },
                                leadingIcon = { Icon(Icons.Default.AccessTime, contentDescription = null) },
                                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                                modifier = Modifier.fillMaxWidth()
                            )
                        }
                    }

                    if (index < aportesColectivos.size - 1) {
                        Spacer(modifier = Modifier.height(8.dp))
                    }
                }

                Button(
                    onClick = { aportesColectivos.add(AporteUI()) },
                    colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF4CAF50)),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Icon(
                        imageVector = Icons.Default.Add,
                        contentDescription = null,
                        tint = Color.White,
                        modifier = Modifier.padding(end = 8.dp)
                    )
                    Text("Agregar Aporte Colectivo", color = Color.White)
                }

                OutlinedTextField(
                    value = tasaColectiva,
                    onValueChange = { tasaColectiva = it },
                    label = { Text("Tasa Colectiva (%)") },
                    leadingIcon = { Icon(Icons.Default.Percent, contentDescription = null) },
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                    modifier = Modifier.fillMaxWidth()
                )
            }
        }

        Button(
            onClick = {
                val aporteDouble = aporte.toDoubleOrNull()
                val tasaDouble = tasa.toDoubleOrNull()?.div(100)
                val aniosInt = numAnios.toIntOrNull()
                val capsPorAnio = capitalizaciones.toIntOrNull()

                if (aporteDouble != null && tasaDouble != null && aniosInt != null && capsPorAnio != null) {
                    viewModel.calcularIndividual(
                        CapitalizacionIndividualRequest(
                            aporte = aporteDouble,
                            tasa = tasaDouble,
                            numAnios = aniosInt,
                            capitalizaciones = capsPorAnio
                        )
                    )
                }
            },
            colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF9130F2)),
            modifier = Modifier
                .fillMaxWidth()
                .height(56.dp),
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

        if (resultadoAPI!=null) {
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
                        text = "Capital acumulado: $${String.format("%.2f", resultadoAPI)}",
                        fontSize = 20.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color(0xFFF2637E)
                    )
                }
            }
        }
    }
}

@Composable
fun CapitalizacionSegurosUI(periodicidad: String) {
    var aportes by remember { mutableStateOf("") }
    var tasa by remember { mutableStateOf("") }
    var anios by remember { mutableStateOf("") }
    var costoSeguro by remember { mutableStateOf("") }
    var calcularRenta by remember { mutableStateOf(false) }
    val viewModel: CapitalizacionViewModel = viewModel()
    val resultadoAPI by viewModel.resultadoSeguros.observeAsState()

    Column(verticalArrangement = Arrangement.spacedBy(16.dp)) {
        Text(
            "Capitalización en Seguros",
            fontSize = 18.sp,
            fontWeight = FontWeight.Bold,
            color = Color(0xFF9130F2)
        )

        Text(
            "Permite calcular montos de seguros y rentas basados en aportes periódicos.",
            fontSize = 14.sp,
            color = Color.Gray
        )

        Card(
            modifier = Modifier.fillMaxWidth(),
            colors = CardDefaults.cardColors(containerColor = Color(0xFFF5F5F5))
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                OutlinedTextField(
                    value = aportes,
                    onValueChange = { aportes = it },
                    label = { Text("Aportes") },
                    leadingIcon = { Icon(Icons.Default.AttachMoney, contentDescription = null) },
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                    modifier = Modifier.fillMaxWidth()
                )

                OutlinedTextField(
                    value = tasa,
                    onValueChange = { tasa = it },
                    label = { Text("Tasa de Interés (%)") },
                    leadingIcon = { Icon(Icons.Default.Percent, contentDescription = null) },
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                    modifier = Modifier.fillMaxWidth()
                )

                OutlinedTextField(
                    value = anios,
                    onValueChange = { anios = it },
                    label = { Text("Años") },
                    leadingIcon = { Icon(Icons.Default.DateRange, contentDescription = null) },
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                    modifier = Modifier.fillMaxWidth()
                )

                OutlinedTextField(
                    value = costoSeguro,
                    onValueChange = { costoSeguro = it },
                    label = { Text("Costo del Seguro") },
                    leadingIcon = { Icon(Icons.Default.Security, contentDescription = null) },
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                    modifier = Modifier.fillMaxWidth()
                )

                Card(
                    modifier = Modifier.fillMaxWidth(),
                    colors = CardDefaults.cardColors(containerColor = Color.White)
                ) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.SpaceBetween,
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(12.dp)
                    ) {
                        Text("¿Calcular Renta?", fontWeight = FontWeight.Medium)
                        Switch(
                            checked = calcularRenta,
                            onCheckedChange = { calcularRenta = it },
                            colors = SwitchDefaults.colors(
                                checkedThumbColor = Color(0xFF9130F2),
                                checkedTrackColor = Color(0xFFD3B0FF)
                            )
                        )
                    }
                }
            }
        }

        Button(
            onClick = {
                val aportesDouble = aportes.toDoubleOrNull()
                val tasaDouble = tasa.toDoubleOrNull()?.div(100)
                val aniosInt = anios.toIntOrNull()
                val costoSeguroDouble = costoSeguro.toDoubleOrNull()

                if (aportesDouble != null && tasaDouble != null && aniosInt != null && costoSeguroDouble != null) {
                    viewModel.calcularSeguros(
                        CapitalizacionSegurosRequest(
                            aportes = aportesDouble,
                            tasaInteres = tasaDouble,
                            anios = aniosInt,
                            costoSeguro = costoSeguroDouble,
                            calcularRenta = calcularRenta
                        )
                    )
                }
            },
            colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF9130F2)),
            modifier = Modifier
                .fillMaxWidth()
                .height(56.dp),
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

        if (resultadoAPI != null) {
            val tipo = if (calcularRenta) "Renta periódica" else "Capital final"
            val valor = if (calcularRenta) resultadoAPI?.rentaPeriodica else resultadoAPI?.capitalFinal

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
                        text = "$tipo: $${String.format("%.2f", valor)}",
                        fontSize = 20.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color(0xFFF2637E)
                    )
                }
            }
        }
    }
}
