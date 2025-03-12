package com.example.myapplication

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController

@Composable
fun SignUpScreen(navController: NavHostController) {
    Box(modifier = Modifier.fillMaxSize()) {
        // Fondo de pantalla
        Image(
            painter = painterResource(id = R.drawable.fondo),
            contentDescription = null,
            modifier = Modifier.fillMaxSize(),
            contentScale = ContentScale.Crop
        )

        // Contenedor blanco
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(top = LocalConfiguration.current.screenHeightDp.dp * 0.1f)
                .background(Color.White, shape = RoundedCornerShape(topStart = 40.dp, topEnd = 40.dp))
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(24.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = "Empieza ahora",
                    fontSize = 32.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color(0xFF3574F2),
                    modifier = Modifier.padding(vertical = 24.dp)
                )

                // Campos de texto
                var nombre by remember { mutableStateOf("") }
                var apellido by remember { mutableStateOf("") }
                var cedula by remember { mutableStateOf("") }
                var telefono by remember { mutableStateOf("") }
                var correo by remember { mutableStateOf("") }
                var password by remember { mutableStateOf("") }
                var passwordVisible by remember { mutableStateOf(false) }

                CustomTextField(label = "Nombre", value = nombre, onValueChange = { nombre = it })
                Spacer(modifier = Modifier.height(16.dp))
                CustomTextField(label = "Apellido", value = apellido, onValueChange = { apellido = it })
                Spacer(modifier = Modifier.height(16.dp))
                CustomTextField(label = "Cédula", value = cedula, onValueChange = { cedula = it })
                Spacer(modifier = Modifier.height(16.dp))
                CustomTextField(label = "Teléfono", value = telefono, onValueChange = { telefono = it })
                Spacer(modifier = Modifier.height(16.dp))
                CustomTextField(label = "Correo", value = correo, onValueChange = { correo = it })
                Spacer(modifier = Modifier.height(16.dp))

                CustomTextField(
                    label = "Contraseña",
                    value = password,
                    onValueChange = { password = it },
                    isPassword = true,
                    passwordVisible = passwordVisible,
                    onPasswordToggle = { passwordVisible = !passwordVisible }
                )
                Spacer(modifier = Modifier.height(24.dp))

                Button(
                    onClick = { navController.navigate("initial") },
                    colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF3574F2)),
                    shape = RoundedCornerShape(15.dp),
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(56.dp)
                ) {
                    Text(text = "Registrarse", color = Color.White, fontSize = 16.sp, fontWeight = FontWeight.Bold)
                }

                Spacer(modifier = Modifier.height(24.dp))

                Text(
                    text = "¿Ya tienes una cuenta? Inicia sesión",
                    fontSize = 14.sp,
                    color = Color(0xFF9130F2),
                    textAlign = TextAlign.Center,
                    modifier = Modifier
                        .clickable {
                            navController.navigate("signIn") // Navega a la pantalla de inicio de sesión
                        }
                )
            }
        }
    }
}