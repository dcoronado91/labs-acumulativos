package com.example.lab8moviles

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.lab8moviles.data.DataStorePreferences
import com.example.lab8moviles.navigation.Destinations
import com.example.lab8moviles.ui.characterdetail.CharacterDetailScreen
import com.example.lab8moviles.ui.locations.LocationDetailScreen
import com.example.lab8moviles.ui.login.LoginScreen
import com.example.lab8moviles.ui.main.MainScreen

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val dataStore = DataStorePreferences(this)
        setContent {
            val navController = rememberNavController()
            val name by dataStore.nameFlow.collectAsState(initial = null)

            Log.d("MainActivity", "Starting app, name in DataStore: $name")

            NavHost(
                navController = navController,
                startDestination = Destinations.Login
            ) {
                composable<Destinations.Login> {
                    LoginScreen(
                        navController = navController,
                        dataStore = dataStore,
                        activity = this@MainActivity
                    )
                }
                composable<Destinations.Main> {
                    MainScreen(
                        rootNavController = navController,
                        activity = this@MainActivity
                    )
                }
                composable<Destinations.CharacterDetail> { backStackEntry ->
                    CharacterDetailScreen(
                        onBack = { navController.popBackStack() },
                        navBackStackEntry = backStackEntry
                    )
                }
                composable<Destinations.LocationDetail> { backStackEntry ->
                    LocationDetailScreen(
                        onBack = { navController.popBackStack() },
                        navBackStackEntry = backStackEntry
                    )
                }
            }
        }
    }
}
