package com.example.myapplication.Screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
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
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.example.myapplication.R
import com.example.myapplication.viewModel.LoginViewModel

@Composable
fun SignInScreen(navController: NavHostController) {
    val primaryColor = Color(0xFF3574F2)
    val linkColor = Color(0xFF9130F2)
    val viewModel: LoginViewModel = viewModel()
    val loginResponse by viewModel.loginResponse.observeAsState();

    LaunchedEffect(loginResponse) {
        if (loginResponse != null) {
            // Aquí rediriges a la pantalla principal o home
            navController.navigate("initial") {
                // Esto elimina la pantalla de login del backstack
                popUpTo("signIn") { inclusive = true }
            }
        }
    }

    var cedula by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var passwordVisible by remember { mutableStateOf(false) }

    Box(modifier = Modifier.fillMaxSize()) {
        Image(
            painter = painterResource(id = R.drawable.fondo),
            contentDescription = null,
            modifier = Modifier.fillMaxSize(),
            contentScale = ContentScale.Crop
        )

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
                    text = "Bienvenido",
                    fontSize = 32.sp,
                    fontWeight = FontWeight.Bold,
                    color = primaryColor,
                    modifier = Modifier.padding(vertical = 24.dp)
                )

                var cedula by remember { mutableStateOf("") }
                var password by remember { mutableStateOf("") }
                var passwordVisible by remember { mutableStateOf(false) }

                CustomTextField("Cédula", cedula, onValueChange = { cedula = it })
                Spacer(modifier = Modifier.height(16.dp))
                CustomTextField(
                    "Contraseña", password,
                    onValueChange = { password = it },
                    isPassword = true,
                    passwordVisible = passwordVisible,
                    onPasswordToggle = { passwordVisible = !passwordVisible }
                )

                Text(
                    text = "¿Olvidaste tu contraseña?",
                    fontSize = 14.sp,
                    color = primaryColor,
                    modifier = Modifier.align(Alignment.End)
                )

                Spacer(modifier = Modifier.height(32.dp))

                Button(
                    onClick = { viewModel.realizarLogin(cedula, password) }, // Llamar al ViewModel
                    colors = ButtonDefaults.buttonColors(containerColor = primaryColor),
                    shape = RoundedCornerShape(15.dp),
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(56.dp)
                ) {
                    Text("Iniciar sesión", color = Color.White, fontSize = 16.sp, fontWeight = FontWeight.Bold)
                }

                Spacer(modifier = Modifier.height(32.dp))

                Text(
                    text = "¿No tienes una cuenta? Regístrate",
                    fontSize = 14.sp,
                    color = linkColor,
                    textAlign = TextAlign.Center,
                    modifier = Modifier.clickable { navController.navigate("signUp") }
                )
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CustomTextField(
    label: String,
    value: String,
    onValueChange: (String) -> Unit,
    isPassword: Boolean = false,
    passwordVisible: Boolean = false,
    onPasswordToggle: (() -> Unit)? = null
) {
    OutlinedTextField(
        value = value,
        onValueChange = onValueChange,
        label = { Text(label) },
        shape = RoundedCornerShape(16.dp),
        singleLine = true,
        visualTransformation = if (isPassword && !passwordVisible) PasswordVisualTransformation() else VisualTransformation.None,
        modifier = Modifier.fillMaxWidth(),
        colors = TextFieldDefaults.outlinedTextFieldColors(
            focusedBorderColor = Color(0xFFD9D9D9),
            unfocusedBorderColor = Color(0xFFD9D9D9)
        )
    )
}
