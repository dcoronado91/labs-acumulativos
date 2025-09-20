package com.example.lab8moviles.ui.main

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Place
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.lab8moviles.ui.characters.CharactersScreen
import com.example.lab8moviles.ui.characterdetail.CharacterDetailScreen
import com.example.lab8moviles.ui.locations.LocationsScreen
import com.example.lab8moviles.ui.locations.LocationDetailScreen
import com.example.lab8moviles.ui.profile.ProfileScreen
import androidx.compose.ui.Modifier
import androidx.compose.foundation.layout.padding


@Composable
fun MainScreen(onLogout: () -> Unit) {
    val navController = rememberNavController()
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentDestination = navBackStackEntry?.destination

    Scaffold(
        bottomBar = {
            NavigationBar {
                NavigationBarItem(
                    selected = currentDestination?.hierarchy?.any { it.route == "characters" } == true,
                    onClick = {
                        navController.navigate("characters") {
                            popUpTo(navController.graph.findStartDestination().id) {
                                saveState = true
                            }
                            launchSingleTop = true
                            restoreState = true
                        }
                    },
                    icon = { Icon(Icons.Filled.Person, contentDescription = "Characters") },
                    label = { Text("Characters") }
                )
                NavigationBarItem(
                    selected = currentDestination?.hierarchy?.any { it.route == "locations" } == true,
                    onClick = {
                        navController.navigate("locations") {
                            popUpTo(navController.graph.findStartDestination().id) {
                                saveState = true
                            }
                            launchSingleTop = true
                            restoreState = true
                        }
                    },
                    icon = { Icon(Icons.Filled.Place, contentDescription = "Locations") },
                    label = { Text("Locations") }
                )
                NavigationBarItem(
                    selected = currentDestination?.hierarchy?.any { it.route == "profile" } == true,
                    onClick = {
                        navController.navigate("profile") {
                            popUpTo(navController.graph.findStartDestination().id) {
                                saveState = true
                            }
                            launchSingleTop = true
                            restoreState = true
                        }
                    },
                    icon = { Icon(Icons.Filled.AccountCircle, contentDescription = "Profile") },
                    label = { Text("Profile") }
                )
            }
        }
    ) { innerPadding -> // Changed to innerPadding to match common convention
        NavHost(
            navController = navController,
            startDestination = "characters",
            modifier = Modifier.padding(innerPadding)
        ) {
            composable("characters") {
                CharactersScreen(onCharacterClick = { id ->
                    navController.navigate("characterDetail/$id")
                })
            }
            composable(
                route = "characterDetail/{characterId}",
                arguments = listOf(navArgument("characterId") { type = NavType.IntType })
            ) { backStackEntry ->
                val characterId = backStackEntry.arguments?.getInt("characterId") ?: 0
                CharacterDetailScreen(characterId = characterId, onBack = { navController.popBackStack() })
            }
            composable("locations") {
                LocationsScreen(onLocationClick = { id ->
                    navController.navigate("locationDetail/$id")
                })
            }
            composable(
                route = "locationDetail/{locationId}",
                arguments = listOf(navArgument("locationId") { type = NavType.IntType })
            ) { backStackEntry ->
                val locationId = backStackEntry.arguments?.getInt("locationId") ?: 0
                LocationDetailScreen(locationId = locationId, onBack = { navController.popBackStack() })
            }
            composable("profile") {
                ProfileScreen(onLogout = onLogout)
            }
        }
    }
}