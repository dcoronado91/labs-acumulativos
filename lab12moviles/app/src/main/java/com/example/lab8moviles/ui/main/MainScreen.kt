package com.example.lab8moviles.ui.main

import androidx.activity.ComponentActivity
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.lab8moviles.data.AppDatabase
import com.example.lab8moviles.navigation.Destinations
import com.example.lab8moviles.ui.characters.CharactersScreen
import com.example.lab8moviles.ui.locations.LocationsScreen
import com.example.lab8moviles.ui.profile.ProfileScreen
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.filled.Person

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainScreen(rootNavController: NavHostController, activity: ComponentActivity) {
    val db = AppDatabase.getDatabase(activity)
    val navController = rememberNavController()
    val currentBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = currentBackStackEntry?.destination?.route ?: "characters"

    Scaffold(
        bottomBar = {
            NavigationBar {
                NavigationBarItem(
                    selected = currentRoute == "characters",
                    onClick = { navController.navigate("characters") { popUpTo(navController.graph.startDestinationId) } },
                    icon = { Icon(Icons.Default.Person, contentDescription = "Characters") },
                    label = { Text("Characters") }
                )
                NavigationBarItem(
                    selected = currentRoute == "locations",
                    onClick = { navController.navigate("locations") { popUpTo(navController.graph.startDestinationId) } },
                    icon = { Icon(Icons.Default.LocationOn, contentDescription = "Locations") },
                    label = { Text("Locations") }
                )
                NavigationBarItem(
                    selected = currentRoute == "profile",
                    onClick = { navController.navigate("profile") { popUpTo(navController.graph.startDestinationId) } },
                    icon = { Icon(Icons.Default.AccountCircle, contentDescription = "Profile") },
                    label = { Text("Profile") }
                )
            }
        }
    ) { padding ->
        NavHost(
            navController = navController,
            startDestination = "characters",
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
        ) {
            composable("characters") {
                CharactersScreen(
                    db = db,
                    onCharacterClick = { id -> rootNavController.navigate(Destinations.CharacterDetail(id)) }
                )
            }
            composable("locations") {
                LocationsScreen(
                    db = db,
                    onLocationClick = { id -> rootNavController.navigate(Destinations.LocationDetail(id)) }
                )
            }
            composable("profile") {
                ProfileScreen(
                    navController = rootNavController,
                    activity = activity
                )
            }
        }
    }
}