package com.example.myapplication.Screens

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.AcUnit
import androidx.compose.material.icons.filled.AccountBalance
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Savings
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.Send
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.WbSunny
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.navigation.NavHostController

// Modelo de datos para las cuentas bancarias según la estructura proporcionada
data class BankAccount(
    val idCuenta: String,
    val tipoCuenta: Int, // 1 = Ahorros, 2 = Corriente (asumiendo esta convención)
    val cedula: String,
    val saldoCuenta: Double,
    val estadoCuenta: Boolean // true = Activa, false = Congelada
) {
    // Función auxiliar para obtener el nombre del tipo de cuenta
    fun getTipoCuentaNombre(): String {
        return when(tipoCuenta) {
            1 -> "Ahorros"
            2 -> "Corriente"
            else -> "Otro"
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BankAccountsScreen(navController: NavHostController) {
    val scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior()
    var showCreateAccountDialog by remember { mutableStateOf(false) }
    var expandedAccountId by remember { mutableStateOf<String?>(null) }

    // Datos de ejemplo usando tu estructura
    val accounts = remember {
        mutableStateListOf(
            BankAccount(
                idCuenta = "AC-001",
                tipoCuenta = 1, // Ahorros
                cedula = "1234567890",
                saldoCuenta = 3249.75,
                estadoCuenta = true
            ),
            BankAccount(
                idCuenta = "CC-002",
                tipoCuenta = 2, // Corriente
                cedula = "0987654321",
                saldoCuenta = 8765.20,
                estadoCuenta = true
            ),
            BankAccount(
                idCuenta = "AC-003",
                tipoCuenta = 1, // Ahorros
                cedula = "5678901234",
                saldoCuenta = 1500.00,
                estadoCuenta = false
            )
        )
    }

    if (showCreateAccountDialog) {
        CreateAccountDialog(
            onDismiss = { showCreateAccountDialog = false },
            onAccountCreated = { newAccount ->
                accounts.add(0, newAccount)
                showCreateAccountDialog = false
            }
        )
    }

    Scaffold(
        topBar = {
            TopAppBar(
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = Color(0xFF1E55BB),
                    titleContentColor = Color.White
                ),
                title = { Text("Mis Cuentas", fontWeight = FontWeight.Bold) },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(
                            Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = "Regresar",
                            tint = Color.White
                        )
                    }
                },
                actions = {
                    IconButton(onClick = { /* Buscar cuentas */ }) {
                        Icon(
                            Icons.Filled.Search,
                            contentDescription = "Buscar",
                            tint = Color.White
                        )
                    }
                },
                scrollBehavior = scrollBehavior
            )
        },
        floatingActionButton = {
            FloatingActionButton(
                onClick = { showCreateAccountDialog = true },
                containerColor = Color(0xFF2962FF),
                contentColor = Color.White
            ) {
                Icon(Icons.Filled.Add, contentDescription = "Agregar cuenta")
            }
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .nestedScroll(scrollBehavior.nestedScrollConnection)
        ) {
            AccountsSummary(accounts)

            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp, vertical = 8.dp)
            ) {
                Text(
                    "Tus Cuentas",
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold
                )
            }

            // Lista de cuentas
            if (accounts.isEmpty()) {
                EmptyAccountsView(onClick = { showCreateAccountDialog = true })
            } else {
                LazyColumn(
                    contentPadding = PaddingValues(horizontal = 16.dp, vertical = 8.dp),
                    verticalArrangement = Arrangement.spacedBy(8.dp),
                    modifier = Modifier.weight(1f)
                ) {
                    items(accounts) { account ->
                        AccountCard(
                            account = account,
                            isExpanded = expandedAccountId == account.idCuenta,
                            onToggleExpand = {
                                expandedAccountId = if (expandedAccountId == account.idCuenta) null else account.idCuenta
                            },
                            onToggleFreeze = {
                                val index = accounts.indexOfFirst { it.idCuenta == account.idCuenta }
                                if (index != -1) {
                                    accounts[index] = account.copy(estadoCuenta = !account.estadoCuenta)
                                }
                            },
                            onViewDetails = {
                                // Navegar a detalles de la cuenta
                                navController.navigate("accountDetails/${account.idCuenta}")
                            }
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun AccountsSummary(accounts: List<BankAccount>) {
    val totalBalance = accounts.sumOf { it.saldoCuenta }
    val activeAccounts = accounts.count { it.estadoCuenta }

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = Color(0xFFF5F9FF))
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Column {
                    Text(
                        "Balance Total",
                        style = MaterialTheme.typography.labelMedium,
                        color = Color.Gray
                    )
                    Row(
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            "$ ${String.format("%,.2f", totalBalance)}",
                            style = MaterialTheme.typography.titleLarge,
                            fontWeight = FontWeight.Bold
                        )
                        Spacer(modifier = Modifier.width(4.dp))
                        Icon(
                            Icons.Filled.Visibility,
                            contentDescription = "Ver Balance",
                            modifier = Modifier.size(16.dp),
                            tint = Color.Gray
                        )
                    }
                }

                Card(
                    shape = RoundedCornerShape(8.dp),
                    colors = CardDefaults.cardColors(containerColor = Color(0xFF2962FF))
                ) {
                    Row(
                        modifier = Modifier.padding(horizontal = 12.dp, vertical = 6.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            "$activeAccounts activas",
                            color = Color.White,
                            fontSize = 12.sp,
                            fontWeight = FontWeight.Medium
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            Divider(color = Color.LightGray.copy(alpha = 0.5f))

            Spacer(modifier = Modifier.height(16.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                AccountTypeCounter(
                    count = accounts.count { it.tipoCuenta == 1 },
                    type = "Ahorros",
                    icon = Icons.Filled.Savings,
                    color = Color(0xFF43A047)
                )

                AccountTypeCounter(
                    count = accounts.count { it.tipoCuenta == 2 },
                    type = "Corriente",
                    icon = Icons.Filled.AccountBalance,
                    color = Color(0xFF1E88E5)
                )

                AccountTypeCounter(
                    count = accounts.count { !it.estadoCuenta },
                    type = "Congeladas",
                    icon = Icons.Filled.AcUnit,
                    color = Color(0xFF757575)
                )
            }
        }
    }
}

@Composable
fun AccountTypeCounter(count: Int, type: String, icon: ImageVector, color: Color) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Box(
            modifier = Modifier
                .size(40.dp)
                .background(color.copy(alpha = 0.1f), CircleShape),
            contentAlignment = Alignment.Center
        ) {
            Icon(
                icon,
                contentDescription = type,
                tint = color,
                modifier = Modifier.size(24.dp)
            )
        }

        Spacer(modifier = Modifier.height(4.dp))

        Text(
            text = count.toString(),
            style = MaterialTheme.typography.bodyLarge,
            fontWeight = FontWeight.Bold
        )

        Text(
            text = type,
            style = MaterialTheme.typography.bodySmall,
            color = Color.Gray
        )
    }
}

@Composable
fun AccountCard(
    account: BankAccount,
    isExpanded: Boolean,
    onToggleExpand: () -> Unit,
    onToggleFreeze: () -> Unit,
    onViewDetails: () -> Unit
) {
    val cardColor = if (!account.estadoCuenta) {
        Color(0xFFF5F5F5)
    } else {
        MaterialTheme.colorScheme.surface
    }

    val accountTypeIcon = if (account.tipoCuenta == 1) {
        Icons.Filled.Savings
    } else {
        Icons.Filled.AccountBalance
    }

    val accountTypeColor = if (account.tipoCuenta == 1) {
        Color(0xFF43A047)
    } else {
        Color(0xFF1E88E5)
    }

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .animateContentSize(),
        shape = RoundedCornerShape(12.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
        colors = CardDefaults.cardColors(containerColor = cardColor)
    ) {
        Column(
            modifier = Modifier.fillMaxWidth()
        ) {
            // Parte superior (siempre visible)
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable { onToggleExpand() }
                    .padding(16.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                // Icono del tipo de cuenta
                Box(
                    modifier = Modifier
                        .size(48.dp)
                        .background(accountTypeColor.copy(alpha = 0.1f), CircleShape),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        accountTypeIcon,
                        contentDescription = account.getTipoCuentaNombre(),
                        tint = accountTypeColor,
                        modifier = Modifier.size(24.dp)
                    )
                }

                Spacer(modifier = Modifier.width(16.dp))

                // Información de la cuenta
                Column(
                    modifier = Modifier.weight(1f)
                ) {
                    Text(
                        text = "Cuenta ${account.idCuenta}",
                        style = MaterialTheme.typography.bodyLarge,
                        fontWeight = FontWeight.Bold
                    )

                    Text(
                        text = "${account.getTipoCuentaNombre()} • CI: ${account.cedula}",
                        style = MaterialTheme.typography.bodyMedium,
                        color = Color.Gray
                    )
                }

                // Estado de la cuenta
                Column(
                    horizontalAlignment = Alignment.End
                ) {
                    Text(
                        text = "$ ${String.format("%,.2f", account.saldoCuenta)}",
                        style = MaterialTheme.typography.bodyLarge,
                        fontWeight = FontWeight.Bold
                    )

                    Text(
                        text = if (account.estadoCuenta) "Activa" else "Congelada",
                        style = MaterialTheme.typography.bodySmall,
                        color = if (account.estadoCuenta) Color(0xFF43A047) else Color(0xFF757575)
                    )
                }
            }

            // Parte expandida (opciones)
            AnimatedVisibility(visible = isExpanded) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp, vertical = 8.dp)
                ) {
                    Divider(color = Color.LightGray.copy(alpha = 0.5f))

                    Spacer(modifier = Modifier.height(16.dp))

                    // Botones de acción
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceEvenly
                    ) {
                        ActionButton(
                            icon = Icons.Filled.Visibility,
                            text = "Ver Detalles",
                            onClick = onViewDetails
                        )

                        ActionButton(
                            icon = if (account.estadoCuenta) Icons.Filled.AcUnit else Icons.Filled.WbSunny,
                            text = if (account.estadoCuenta) "Congelar" else "Descongelar",
                            onClick = onToggleFreeze
                        )

                        ActionButton(
                            icon = Icons.Filled.Send,
                            text = "Transferir",
                            onClick = { /* Acción de transferir */ }
                        )
                    }

                    Spacer(modifier = Modifier.height(8.dp))
                }
            }
        }
    }
}

@Composable
fun ActionButton(icon: ImageVector, text: String, onClick: () -> Unit) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .padding(8.dp)
            .clickable(onClick = onClick)
    ) {
        Icon(
            icon,
            contentDescription = text,
            tint = Color(0xFF2962FF),
            modifier = Modifier.size(24.dp)
        )

        Spacer(modifier = Modifier.height(4.dp))

        Text(
            text = text,
            style = MaterialTheme.typography.bodySmall,
            color = Color.Gray,
            textAlign = TextAlign.Center
        )
    }
}

@Composable
fun EmptyAccountsView(onClick: () -> Unit) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(32.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Icon(
            Icons.Filled.AccountBalance,
            contentDescription = null,
            modifier = Modifier.size(80.dp),
            tint = Color.LightGray
        )

        Spacer(modifier = Modifier.height(16.dp))

        Text(
            "No tienes cuentas registradas",
            style = MaterialTheme.typography.titleMedium,
            fontWeight = FontWeight.Bold,
            textAlign = TextAlign.Center
        )

        Spacer(modifier = Modifier.height(8.dp))

        Text(
            "Añade tu primera cuenta bancaria para empezar a gestionar tus finanzas",
            style = MaterialTheme.typography.bodyMedium,
            color = Color.Gray,
            textAlign = TextAlign.Center
        )

        Spacer(modifier = Modifier.height(24.dp))

        Button(
            onClick = onClick,
            colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF2962FF))
        ) {
            Icon(
                Icons.Filled.Add,
                contentDescription = null,
                modifier = Modifier.size(20.dp)
            )

            Spacer(modifier = Modifier.width(8.dp))

            Text("Añadir Cuenta")
        }
    }
}

@Composable
fun CreateAccountDialog(
    onDismiss: () -> Unit,
    onAccountCreated: (BankAccount) -> Unit
) {
    var selectedAccountType by remember { mutableIntStateOf(1) } // 1 = Ahorros, 2 = Corriente
    var cedula by remember { mutableStateOf("") }
    var accountId by remember { mutableStateOf("") }
    var initialBalance by remember { mutableStateOf("") }
    var isFormValid by remember { mutableStateOf(false) }

    // Validar el formulario
    LaunchedEffect(cedula, accountId, initialBalance) {
        isFormValid = cedula.length >= 7 &&
                accountId.isNotEmpty() &&
                initialBalance.toDoubleOrNull() != null
    }

    Dialog(onDismissRequest = onDismiss) {
        Surface(
            shape = RoundedCornerShape(16.dp),
            color = MaterialTheme.colorScheme.surface
        ) {
            Column(
                modifier = Modifier
                    .padding(24.dp)
                    .fillMaxWidth()
            ) {
                // Título
                Text(
                    "Nueva Cuenta Bancaria",
                    style = MaterialTheme.typography.titleLarge,
                    fontWeight = FontWeight.Bold
                )

                Spacer(modifier = Modifier.height(16.dp))

                // Tipo de cuenta
                Text(
                    "Tipo de Cuenta",
                    style = MaterialTheme.typography.bodyMedium,
                    color = Color.Gray
                )

                Spacer(modifier = Modifier.height(8.dp))

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    AccountTypeButton(
                        text = "Ahorros",
                        icon = Icons.Filled.Savings,
                        isSelected = selectedAccountType == 1,
                        onClick = { selectedAccountType = 1 },
                        modifier = Modifier.weight(1f)
                    )

                    AccountTypeButton(
                        text = "Corriente",
                        icon = Icons.Filled.AccountBalance,
                        isSelected = selectedAccountType == 2,
                        onClick = { selectedAccountType = 2 },
                        modifier = Modifier.weight(1f)
                    )
                }

                Spacer(modifier = Modifier.height(16.dp))

                // Cédula
                Text(
                    "Cédula o Identificación",
                    style = MaterialTheme.typography.bodyMedium,
                    color = Color.Gray
                )

                Spacer(modifier = Modifier.height(8.dp))

                OutlinedTextField(
                    value = cedula,
                    onValueChange = { input ->
                        // Solo permitir números en la cédula
                        if (input.all { it.isDigit() || it == '-' }) {
                            cedula = input
                        }
                    },
                    placeholder = { Text("Ingrese su número de cédula") },
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                    singleLine = true,
                    modifier = Modifier.fillMaxWidth()
                )

                Spacer(modifier = Modifier.height(16.dp))

                // ID de cuenta
                Text(
                    "ID de Cuenta",
                    style = MaterialTheme.typography.bodyMedium,
                    color = Color.Gray
                )

                Spacer(modifier = Modifier.height(8.dp))

                OutlinedTextField(
                    value = accountId,
                    onValueChange = { accountId = it },
                    placeholder = { Text("Ej: AC-001") },
                    singleLine = true,
                    modifier = Modifier.fillMaxWidth()
                )

                Spacer(modifier = Modifier.height(16.dp))

                // Saldo inicial
                Text(
                    "Saldo Inicial",
                    style = MaterialTheme.typography.bodyMedium,
                    color = Color.Gray
                )

                Spacer(modifier = Modifier.height(8.dp))

                OutlinedTextField(
                    value = initialBalance,
                    onValueChange = { input ->
                        // Validar que sea un número decimal válido
                        if (input.isEmpty() || input.matches(Regex("^\\d*\\.?\\d*\$"))) {
                            initialBalance = input
                        }
                    },
                    placeholder = { Text("0.00") },
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Decimal),
                    leadingIcon = { Text("$", fontWeight = FontWeight.Bold) },
                    singleLine = true,
                    modifier = Modifier.fillMaxWidth()
                )

                Spacer(modifier = Modifier.height(24.dp))

                // Botones
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.End
                ) {
                    TextButton(onClick = onDismiss) {
                        Text("Cancelar")
                    }

                    Spacer(modifier = Modifier.width(8.dp))

                    Button(
                        onClick = {
                            val newAccount = BankAccount(
                                idCuenta = accountId,
                                tipoCuenta = selectedAccountType,
                                cedula = cedula,
                                saldoCuenta = initialBalance.toDoubleOrNull() ?: 0.0,
                                estadoCuenta = true
                            )
                            onAccountCreated(newAccount)
                        },
                        enabled = isFormValid,
                        colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF2962FF))
                    ) {
                        Text("Guardar")
                    }
                }
            }
        }
    }
}

@Composable
fun AccountTypeButton(
    text: String,
    icon: ImageVector,
    isSelected: Boolean,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    val backgroundColor = if (isSelected) {
        Color(0xFF2962FF).copy(alpha = 0.1f)
    } else {
        Color.LightGray.copy(alpha = 0.1f)
    }

    val contentColor = if (isSelected) {
        Color(0xFF2962FF)
    } else {
        Color.Gray
    }

    val borderColor = if (isSelected) {
        Color(0xFF2962FF)
    } else {
        Color.Transparent
    }

    Surface(
        modifier = modifier
            .height(56.dp)
            .clickable(onClick = onClick),
        shape = RoundedCornerShape(8.dp),
        color = backgroundColor,
        border = BorderStroke(1.dp, borderColor)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center
        ) {
            Icon(
                icon,
                contentDescription = null,
                tint = contentColor,
                modifier = Modifier.size(18.dp)
            )

            Spacer(modifier = Modifier.width(8.dp))

            Text(
                text = text,
                style = MaterialTheme.typography.bodyMedium,
                color = contentColor
            )
        }
    }
}