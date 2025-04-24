package com.example.myapplication.Screens

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
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.example.myapplication.R
import com.example.myapplication.viewModel.RegisterViewModel

@Composable
fun SignUpScreen(navController: NavHostController, viewModel: RegisterViewModel = viewModel()) {
    // Variables de estado locales para los campos de texto
    var nombre by remember { mutableStateOf(viewModel.name) }
    var apellido by remember { mutableStateOf(viewModel.lastname) }
    var cedula by remember { mutableStateOf(viewModel.identification) }
    var telefono by remember { mutableStateOf(viewModel.cellPhone) }
    var correo by remember { mutableStateOf("") }
    var password by remember { mutableStateOf(viewModel.password) }
    var passwordVisible by remember { mutableStateOf(false) }

    // Observamos el estado de la respuesta y la carga
    val isLoading = viewModel.isLoading
    val errorMessage = viewModel.errorMessage
    val jwtResponse = viewModel.jwtResponse

    // Si la solicitud fue exitosa, redirigimos a la pantalla principal
    if (jwtResponse!=null) {
        LaunchedEffect(jwtResponse) {
            // Aquí redirigimos a la pantalla principal
            navController.navigate("initial") {
                popUpTo("signUp") { inclusive = true } // Esto asegura que no puedas regresar a la pantalla de registro
            }
        }
    }

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
                CustomTextField(label = "Nombre", value = nombre, onValueChange = {
                    nombre = it
                    viewModel.name = it // Sincronizamos el valor en el ViewModel
                })
                Spacer(modifier = Modifier.height(16.dp))
                CustomTextField(label = "Apellido", value = apellido, onValueChange = {
                    apellido = it
                    viewModel.lastname = it // Sincronizamos el valor en el ViewModel
                })
                Spacer(modifier = Modifier.height(16.dp))
                CustomTextField(label = "Cédula", value = cedula, onValueChange = {
                    cedula = it
                    viewModel.identification = it // Sincronizamos el valor en el ViewModel
                })
                Spacer(modifier = Modifier.height(16.dp))
                CustomTextField(label = "Teléfono", value = telefono, onValueChange = {
                    telefono = it
                    viewModel.cellPhone = it // Sincronizamos el valor en el ViewModel
                })
                Spacer(modifier = Modifier.height(16.dp))
                CustomTextField(label = "Correo", value = correo, onValueChange = {
                    correo = it
                })
                Spacer(modifier = Modifier.height(16.dp))

                CustomTextField(
                    label = "Contraseña",
                    value = password,
                    onValueChange = {
                        password = it
                        viewModel.password = it // Sincronizamos el valor en el ViewModel
                    },
                    isPassword = true,
                    passwordVisible = passwordVisible,
                    onPasswordToggle = { passwordVisible = !passwordVisible }
                )
                Spacer(modifier = Modifier.height(24.dp))

                // Mostrar indicador de carga
                if (isLoading) {
                    CircularProgressIndicator(modifier = Modifier.padding(16.dp))
                } else {
                    Button(
                        onClick = {
                            // Llamamos a la función de registro
                            viewModel.registerUser()
                        },
                        colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF3574F2)),
                        shape = RoundedCornerShape(15.dp),
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(56.dp)
                    ) {
                        Text(text = "Registrarse", color = Color.White, fontSize = 16.sp, fontWeight = FontWeight.Bold)
                    }
                }

                // Mostrar error si existe
                errorMessage?.let {
                    Text(
                        text = it,
                        color = Color.Red,
                        fontSize = 14.sp,
                        modifier = Modifier.padding(top = 8.dp)
                    )
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
