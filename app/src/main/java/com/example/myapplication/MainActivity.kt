package com.example.myapplication

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.myapplication.ui.theme.MyApplicationTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            MyApplicationTheme {
                val navController = rememberNavController()
                NavHost(navController = navController, startDestination = "welcome") {
                    composable("welcome") { WelcomeScreen(navController) } // ✅ Agregado
                    composable("signIn") { SignInScreen(navController) }
                    composable("signUp") { SignUpScreen(navController) }
                    composable("initial") { InitialView(navController) }
                    composable("interesSimpleScreen") { InteresSimpleScreen(navController) }
                    composable("interesCompuestoScreen") { InteresCompuestoScreen(navController) }
                    composable("anualidadesScreen") { AnualidadesScreen(navController) }
                    composable("gradAritmeticoScreen") { GradienteAritmeticoScreen(navController) }

                }
            }
        }
    }
}



