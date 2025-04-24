package com.example.myapplication

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.myapplication.Screens.AmortizacionScreen
import com.example.myapplication.Screens.AnualidadesScreen
import com.example.myapplication.Screens.BankAccount
import com.example.myapplication.Screens.BankAccountsScreen
import com.example.myapplication.Screens.BonosScreen
import com.example.myapplication.Screens.CapitalizacionScreen
import com.example.myapplication.Screens.GradienteAritmeticoScreen
import com.example.myapplication.Screens.GradienteGeometricoScreen
import com.example.myapplication.Screens.InflacionScreen
import com.example.myapplication.Screens.InteresCompuestoScreen
import com.example.myapplication.Screens.InteresSimpleScreen
import com.example.myapplication.Screens.PrestamosScreen
import com.example.myapplication.Screens.SignInScreen
import com.example.myapplication.Screens.SignUpScreen
import com.example.myapplication.Screens.TasaInteresScreen
import com.example.myapplication.Screens.TirScreen
import com.example.myapplication.Screens.WelcomeScreen
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
                    composable("tasaInteresScreen") { TasaInteresScreen(navController) }
                    composable("interesSimpleScreen") { InteresSimpleScreen(navController) }
                    composable("interesCompuestoScreen") { InteresCompuestoScreen(navController) }
                    composable("anualidadesScreen") { AnualidadesScreen(navController) }
                    composable("gradAritmeticoScreen") { GradienteAritmeticoScreen(navController) }
                    composable("AmortizacionScreen") { AmortizacionScreen(navController) }
                    composable("TirScreen") { TirScreen(navController) }
                    composable("gradGeometricoScreen") { GradienteGeometricoScreen(navController) }
                    composable("bonosScreen") { BonosScreen(navController) }
                    composable("inflacionScreen") { InflacionScreen(navController) }
                    composable("capitalizacionScreen") { CapitalizacionScreen(navController) }
                    composable("cuentas") { BankAccountsScreen(navController) }
                    composable("prestamos") { PrestamosScreen() }
                }
            }
        }
    }
}



