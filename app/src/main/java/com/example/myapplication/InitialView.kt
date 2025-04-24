package com.example.myapplication

import android.content.Intent
import android.net.Uri
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowForward
import androidx.compose.material.icons.automirrored.filled.MenuBook
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun InitialView(navController: NavHostController) {
    val scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior()

    Scaffold(
        topBar = {
            TopAppBar(
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = Color(0xFF1E55BB),
                    titleContentColor = Color.White
                ),
                title = { Text("SinSync", fontWeight = FontWeight.Bold) },
                actions = {
                    IconButton(onClick = { /* Notificaciones */ }) {
                        Icon(Icons.Filled.Notifications, contentDescription = "Notificaciones", tint = Color.White)
                    }
                    IconButton(onClick = { /* Configuración */ }) {
                        Icon(Icons.Filled.Settings, contentDescription = "Configuración", tint = Color.White)
                    }
                },
                scrollBehavior = scrollBehavior
            )
        },
        bottomBar = { BottomNavigationBar(navController) }
    ) { paddingValues ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .nestedScroll(scrollBehavior.nestedScrollConnection)
        ) {
            item { BalanceCard() }
            item { QuickActions(navController) }
            item { FeaturedTools() }
            item { TransactionHistory(navController) }
        }
    }
}

@Composable
fun BalanceCard() {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = Color(0xFF2962FF))
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                "Balance Disponible",
                fontSize = 14.sp,
                color = Color.White.copy(alpha = 0.8f)
            )
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.padding(vertical = 8.dp)
            ) {
                Text(
                    "$500,000",
                    fontSize = 36.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.White
                )
                IconButton(
                    onClick = { /* Mostrar/ocultar balance */ },
                    modifier = Modifier.size(24.dp)
                ) {
                    Icon(
                        Icons.Filled.Visibility,
                        contentDescription = "Mostrar/Ocultar Balance",
                        tint = Color.White.copy(alpha = 0.7f)
                    )
                }
            }

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 16.dp),
                horizontalArrangement = Arrangement.SpaceEvenly
            ) {
                QuickActionButton(
                    icon = Icons.Filled.AddCircle,
                    text = "Depositar",
                    onClick = { /* Acción Depositar */ }
                )
                QuickActionButton(
                    icon = Icons.Filled.Send,
                    text = "Transferir",
                    onClick = { /* Acción Transferir */ }
                )
                QuickActionButton(
                    icon = Icons.Filled.History,
                    text = "Historial",
                    onClick = { /* Acción Historial */ }
                )
            }
        }
    }
}

@Composable
fun QuickActionButton(icon: ImageVector, text: String, onClick: () -> Unit) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier.clickable(onClick = onClick)
    ) {
        Box(
            modifier = Modifier
                .size(40.dp)
                .background(Color.White.copy(alpha = 0.2f), shape = CircleShape),
            contentAlignment = Alignment.Center
        ) {
            Icon(icon, contentDescription = text, tint = Color.White)
        }
        Spacer(modifier = Modifier.height(4.dp))
        Text(text, color = Color.White, fontSize = 12.sp)
    }
}

@Composable
fun QuickActions(navController: NavHostController) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
        shape = RoundedCornerShape(12.dp)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(
                "Herramientas rápidas",
                fontSize = 16.sp,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.onSurface
            )

            Spacer(modifier = Modifier.height(12.dp))

            LazyRow(
                horizontalArrangement = Arrangement.spacedBy(12.dp),
                contentPadding = PaddingValues(vertical = 8.dp)
            ) {
                val quickTools = listOf(
                    Triple("Interés Simple", Icons.Filled.Calculate, "interesSimpleScreen"),
                    Triple("Amortización", Icons.Filled.Payment, "AmortizacionScreen"),
                    Triple("TIR", Icons.Filled.TrendingUp, "TirScreen"),
                    Triple("Bonos", Icons.Filled.Description, "bonosScreen")
                )

                items(quickTools) { (title, icon, route) ->
                    QuickToolItem(title, icon) {
                        navController.navigate(route)
                    }
                }
            }
        }
    }
}

@Composable
fun QuickToolItem(title: String, icon: ImageVector, onClick: () -> Unit) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .width(80.dp)
            .clickable(onClick = onClick)
    ) {
        Box(
            modifier = Modifier
                .size(50.dp)
                .background(Color(0xFFE3F2FD), shape = RoundedCornerShape(12.dp)),
            contentAlignment = Alignment.Center
        ) {
            Icon(
                icon,
                contentDescription = title,
                tint = Color(0xFF2962FF),
                modifier = Modifier.size(30.dp)
            )
        }
        Spacer(modifier = Modifier.height(4.dp))
        Text(
            title,
            fontSize = 12.sp,
            textAlign = TextAlign.Center,
            maxLines = 2,
            overflow = TextOverflow.Ellipsis
        )
    }
}

@Composable
fun FeaturedTools() {
    val context = LocalContext.current
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
    ) {
        Text(
            "Destacados",
            fontSize = 18.sp,
            fontWeight = FontWeight.Bold,
            color = MaterialTheme.colorScheme.onSurface
        )

        Spacer(modifier = Modifier.height(12.dp))

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            FeaturedToolCard(
                title = "Aprende finanzas",
                description = "Cursos interactivos para mejorar tus conocimientos",
                icon = Icons.Filled.School,
                backgroundColor = Color(0xFFE8F5E9),
                contentColor = Color(0xFF2E7D32),
                modifier = Modifier.weight(1f)
            ) {
                val intent = Intent(Intent.ACTION_VIEW, Uri.parse("https://www.youtube.com/playlist?list=PL4ITFgAw-QGOwVRaUYYqO7rkCQc0D8YdV"))
                context.startActivity(intent)
            }

            FeaturedToolCard(
                title = "Evaluación",
                description = "Analiza y compara distintas alternativas financieras",
                icon = Icons.Filled.Assessment,
                backgroundColor = Color(0xFFE1F5FE),
                contentColor = Color(0xFF0277BD),
                modifier = Modifier.weight(1f)
            ) {
                //Vista de evaluacion
            }
        }
    }
}

@Composable
fun FeaturedToolCard(
    title: String,
    description: String,
    icon: ImageVector,
    backgroundColor: Color,
    contentColor: Color,
    modifier: Modifier = Modifier,
    onClick: () -> Unit
) {
    Card(
        modifier = modifier
            .height(140.dp)
            .clickable(onClick = onClick),
        colors = CardDefaults.cardColors(containerColor = backgroundColor),
        shape = RoundedCornerShape(12.dp)
    ) {
        Column(
            modifier = Modifier
                .padding(12.dp)
                .fillMaxSize()
        ) {
            Icon(
                icon,
                contentDescription = null,
                tint = contentColor,
                modifier = Modifier.size(32.dp)
            )

            Spacer(modifier = Modifier.height(12.dp))

            Text(
                title,
                fontSize = 14.sp,
                fontWeight = FontWeight.Bold,
                color = contentColor
            )

            Spacer(modifier = Modifier.height(4.dp))

            Text(
                description,
                fontSize = 12.sp,
                color = contentColor.copy(alpha = 0.7f),
                maxLines = 2,
                overflow = TextOverflow.Ellipsis
            )
        }
    }
}

@Composable
fun TransactionHistory(navController: NavHostController) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(
                "Herramientas financieras",
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.onSurface
            )

            Spacer(modifier = Modifier.weight(1f))
        }

        Spacer(modifier = Modifier.height(8.dp))

        val transactions = listOf(
            Triple("Anualidades", "Cálculos de anualidades anticipadas y vencidas", "anualidadesScreen"),
            Triple("Tasa de Interés", "Conversión entre tasas nominales y efectivas", "tasaInteresScreen"),
            Triple("Interés Compuesto", "Cálculo de montos con capitalización", "interesCompuestoScreen"),
            Triple("Gradiente Aritmético", "Series de pagos con incremento constante", "gradAritmeticoScreen"),
            Triple("Gradiente Geométrico", "Series de pagos con incremento porcentual", "gradGeometricoScreen"),
            Triple("Inflación", "Análisis del impacto inflacionario", "inflacionScreen"),
            Triple("Capitalización", "Esquemas de capitalización", "capitalizacionScreen")
        )

        Column(
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            transactions.take(7).forEach { (title, description, route) ->
                FinancialToolItem(
                    title = title,
                    description = description,
                    onClick = { navController.navigate(route) }
                )
            }
        }
    }
}

@Composable
fun FinancialToolItem(title: String, description: String, onClick: () -> Unit) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable(onClick = onClick),
        shape = RoundedCornerShape(8.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 1.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(12.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Box(
                modifier = Modifier
                    .size(40.dp)
                    .background(Color(0xFFE3F2FD), shape = CircleShape),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = title.first().toString(),
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color(0xFF2962FF)
                )
            }

            Column(
                modifier = Modifier
                    .weight(1f)
                    .padding(horizontal = 12.dp)
            ) {
                Text(
                    title,
                    fontSize = 15.sp,
                    fontWeight = FontWeight.Medium,
                    color = MaterialTheme.colorScheme.onSurface
                )

                Text(
                    description,
                    fontSize = 12.sp,
                    color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.6f),
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
            }

            Icon(
                Icons.AutoMirrored.Filled.ArrowForward,
                contentDescription = null,
                tint = Color(0xFF2962FF),
                modifier = Modifier.size(16.dp)
            )
        }
    }
}

@Composable
fun BottomNavigationBar(navController: NavHostController) {
    val items = listOf(
        Triple("Inicio", Icons.Filled.Home, "initialView"),
        Triple("Herramientas", Icons.Filled.Build, "tools"),
        Triple("Préstamos", Icons.Filled.AttachMoney, "prestamos"),
        Triple("Perfil", Icons.Filled.Person, "cuentas")
    )

    var selectedIndex by remember { mutableIntStateOf(0) }

    NavigationBar(
        containerColor = MaterialTheme.colorScheme.surface,
        tonalElevation = 8.dp
    ) {
        items.forEachIndexed { index, (title, icon, route) ->
            NavigationBarItem(
                icon = {
                    Icon(
                        icon,
                        contentDescription = title,
                        modifier = Modifier.size(26.dp)
                    )
                },
                label = {
                    Text(
                        title,
                        fontSize = 11.sp
                    )
                },
                selected = selectedIndex == index,
                onClick = {
                    selectedIndex = index
                    navController.navigate(route) {
                        popUpTo(navController.graph.findStartDestination().id) {
                            saveState = true
                        }
                        launchSingleTop = true
                        restoreState = true
                    }
                },
                colors = NavigationBarItemDefaults.colors(
                    selectedIconColor = Color(0xFF2962FF),
                    selectedTextColor = Color(0xFF2962FF),
                    indicatorColor = Color(0xFFE3F2FD)
                )
            )
        }
    }
}
