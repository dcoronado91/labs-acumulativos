package com.example.lab8moviles

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.lab8moviles.ui.login.LoginScreen
import com.example.lab8moviles.ui.main.MainScreen
import com.example.lab8moviles.ui.theme.Lab8MovilesTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            Lab8MovilesTheme {
                AppNavHost()
            }
        }
    }
}

@Composable
fun AppNavHost() {
    val navController = rememberNavController()
    NavHost(navController, startDestination = "login") {
        composable("login") {
            LoginScreen(
                onStart = {
                    navController.navigate("main") {
                        popUpTo("login") { inclusive = true }
                    }
                },
                logoUrl = "https://1000logos.net/wp-content/uploads/2022/03/Rick-and-Morty.png"
            )
        }
        composable("main") {
            MainScreen(
                onLogout = {
                    navController.navigate("login") {
                        popUpTo(0) { inclusive = true }
                    }
                }
            )
        }
    }
}