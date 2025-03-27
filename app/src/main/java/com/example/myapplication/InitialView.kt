package com.example.myapplication

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowForward
import androidx.compose.material.icons.automirrored.filled.MenuBook
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController

@Composable
fun InitialView(navController: NavHostController) {
    Scaffold(
        bottomBar = { BottomNavigationBar(navController) }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
        ) {
            BalanceHeader()
            TransactionHistory(navController) // Pasamos el navController correctamente
        }
    }
}

@Composable
fun BalanceHeader() {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .background(Color(0xFF2962FF))
            .padding(16.dp),
        contentAlignment = Alignment.Center
    ) {
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.End
            ) {
                IconButton(onClick = { /* Configuración */ }) {
                    Icon(Icons.Filled.Settings, contentDescription = "Configuración", tint = Color.White)
                }
            }
            Text("SinSync", fontSize = 20.sp, fontWeight = FontWeight.Bold, color = Color.White)
            Text("$500,000", fontSize = 32.sp, fontWeight = FontWeight.Bold, color = Color.White)
            Text("Balance Disponible", fontSize = 14.sp, color = Color.White)
            Spacer(modifier = Modifier.height(16.dp))
            Row(
                horizontalArrangement = Arrangement.SpaceEvenly,
                modifier = Modifier.fillMaxWidth()
            ) {
                IconButton(onClick = { /* Acción 1 */ }) {
                    Icon(Icons.Filled.CreditCard, contentDescription = "Tarjeta", tint = Color.White)
                }
                IconButton(onClick = { /* Acción 2 */ }) {
                    Icon(Icons.Filled.AttachMoney, contentDescription = "Dinero", tint = Color.White)
                }
                IconButton(onClick = { /* Acción 3 */ }) {
                    Icon(Icons.Filled.SyncAlt, contentDescription = "Transacciones", tint = Color.White)
                }
            }
        }
    }
}

@Composable
fun TransactionHistory(navController: NavHostController) {
    Column(Modifier.padding(16.dp)) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .fillMaxWidth()
                .clickable { /* Ir a pantalla de transacciones */ }
        ) {
            Text("Historial de transacciones", fontSize = 18.sp, fontWeight = FontWeight.Bold)
            Spacer(modifier = Modifier.weight(1f))
            Icon(Icons.AutoMirrored.Filled.ArrowForward, contentDescription = "Más")
        }
        Spacer(modifier = Modifier.height(16.dp))

        // Lista de transacciones con navegación
        val transactions = listOf(
            "Tasa de Interés" to "tasaInteresScreen", // Nuevo botón agregado
            "Anualidades" to "anualidadesScreen",
            "Interés Simple" to "interesSimpleScreen",
            "Interés Compuesto" to "interesCompuestoScreen",
            "Gradiente Aritmético" to "gradAritmeticoScreen"
        )

        transactions.forEach { (title, route) ->
            Button(
                onClick = { navController.navigate(route) },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 6.dp),
                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF2962FF))
            ) {
                Text(title, color = Color.White, fontSize = 16.sp, fontWeight = FontWeight.Bold)
            }
        }
    }
}


@Composable
fun BottomNavigationBar(navController: NavHostController) {
    val items = listOf(
        "Inicio" to Icons.Filled.Home,
        "Aprende" to Icons.AutoMirrored.Filled.MenuBook,
        "Préstamo" to Icons.Filled.AttachMoney,
        "Métricas" to Icons.Filled.BarChart,
        "Cuenta" to Icons.Filled.Person
    )

    var selectedIndex by remember { mutableIntStateOf(0) }

    NavigationBar {
        items.forEachIndexed { index, (title, icon) ->
            NavigationBarItem(
                icon = { Icon(icon, contentDescription = title) },
                label = { Text(title) },
                selected = selectedIndex == index,
                onClick = {
                    selectedIndex = index
                    when (title) {
                        "Inicio" -> navController.navigate("initialView")
                        "Aprende" -> navController.navigate("learn")
                        "Préstamo" -> navController.navigate("loan")
                        "Métricas" -> navController.navigate("metrics")
                        "Cuenta" -> navController.navigate("account")
                    }
                }
            )
        }
    }
}
