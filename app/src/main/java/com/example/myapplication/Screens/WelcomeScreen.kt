package com.example.myapplication.Screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.example.myapplication.R

@Composable
fun WelcomeScreen(navController: NavHostController) {
    Box(
        modifier = Modifier.fillMaxSize()
    ) {
        // Imagen de fondo
        Image(
            painter = painterResource(id = R.drawable.fondo),
            contentDescription = null,
            modifier = Modifier.fillMaxSize(),
            contentScale = ContentScale.Crop
        )

        // Logo en la parte superior
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 176.dp),
            contentAlignment = Alignment.TopCenter
        ) {
            Image(
                painter = painterResource(id = R.drawable.logosinsyncedi),
                contentDescription = "Logo",
                modifier = Modifier
                    .size(120.dp)
                    .clip(RoundedCornerShape(68.5.dp)),
                contentScale = ContentScale.Crop
            )
        }

        // Texto "Bienvenido"
        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = "!Bienvenido!",
                fontSize = 28.sp,
                fontWeight = FontWeight.Bold,
                color = Color.White,
                modifier = Modifier.padding(top = 120.dp)
            )
        }
    }

    // 🔹 Pasamos el navController a los botones
    TwoButtonsExample(navController)
}




@Composable
fun TwoButtonsExample(navController: NavHostController) {
    var activeButton by remember { mutableStateOf("right") }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(bottom = 50.dp), // 🔼 Subimos los botones un poco
        contentAlignment = Alignment.BottomCenter
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {
            CustomButton(
                text = "Iniciar sesión",
                isPressed = activeButton == "left",
                onClick = {
                    activeButton = "left"
                    navController.navigate("signIn") // 🚀 Navega a la pantalla de inicio de sesión
                },
                roundedCorner = RoundedCornerShape(20.dp)
            )
            CustomButton(
                text = "Registrarse",
                isPressed = activeButton == "right",
                onClick = {
                    activeButton = "right"
                    navController.navigate("signUp") // 🚀 Navega a la pantalla de registro
                },
                roundedCorner = RoundedCornerShape(20.dp)
            )
        }
    }
}



@Composable
fun CustomButton(text: String, isPressed: Boolean, onClick: () -> Unit, roundedCorner: RoundedCornerShape) {
    val configuration = LocalConfiguration.current
    val buttonWidth = configuration.screenWidthDp.dp * 0.5f
    val buttonHeight = configuration.screenHeightDp.dp * 0.08f

    val backgroundColor = if (isPressed) Color.White else Color.Transparent
    val textColor = if (isPressed) Color(0xFF3574F2) else Color.White

    Surface(
        modifier = Modifier
            .width(buttonWidth)
            .height(buttonHeight)
            .clip(roundedCorner),
        color = backgroundColor
    ) {
        TextButton(
            onClick = onClick,
            modifier = Modifier.fillMaxSize(),
            colors = ButtonDefaults.textButtonColors(contentColor = textColor)
        ) {
            Text(text = text, color = textColor, fontWeight = FontWeight.Bold)
        }
    }
}
