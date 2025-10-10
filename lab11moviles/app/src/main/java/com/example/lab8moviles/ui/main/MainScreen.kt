package com.example.lab8moviles.ui.main

import android.app.Activity
import androidx.activity.compose.BackHandler
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Place
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.NavDestination.Companion.hasRoute
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.lab8moviles.navigation.Destinations
import com.example.lab8moviles.ui.characters.CharactersScreen
import com.example.lab8moviles.ui.locations.LocationsScreen
import com.example.lab8moviles.ui.profile.ProfileScreen
import com.example.lab8moviles.data.DataStorePreferences

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainScreen(rootNavController: NavHostController, activity: Activity) {
    val nestedNavController = rememberNavController()
    val currentDestination = nestedNavController.currentBackStackEntryAsState().value?.destination

    Scaffold(
        bottomBar = {
            NavigationBar {
                NavigationBarItem(
                    selected = currentDestination?.hasRoute(Destinations.Characters::class) == true,
                    onClick = {
                        nestedNavController.navigate(Destinations.Characters) {
                            popUpTo(nestedNavController.graph.startDestinationId) { saveState = true }
                            launchSingleTop = true
                            restoreState = true
                        }
                    },
                    icon = { Icon(Icons.Filled.Person, contentDescription = "Characters") },
                    label = { Text("Characters") }
                )
                NavigationBarItem(
                    selected = currentDestination?.hasRoute(Destinations.Locations::class) == true,
                    onClick = {
                        nestedNavController.navigate(Destinations.Locations) {
                            popUpTo(nestedNavController.graph.startDestinationId) { saveState = true }
                            launchSingleTop = true
                            restoreState = true
                        }
                    },
                    icon = { Icon(Icons.Filled.Place, contentDescription = "Locations") },
                    label = { Text("Locations") }
                )
                NavigationBarItem(
                    selected = currentDestination?.hasRoute(Destinations.Profile::class) == true,
                    onClick = {
                        nestedNavController.navigate(Destinations.Profile) {
                            popUpTo(nestedNavController.graph.startDestinationId) { saveState = true }
                            launchSingleTop = true
                            restoreState = true
                        }
                    },
                    icon = { Icon(Icons.Filled.AccountCircle, contentDescription = "Profile") },
                    label = { Text("Profile") }
                )
            }
        }
    ) { innerPadding ->
        NavHost(
            navController = nestedNavController,
            startDestination = Destinations.Characters,
            modifier = Modifier.padding(innerPadding)
        ) {
            composable<Destinations.Characters> {
                CharactersScreen(
                    onCharacterClick = { id ->
                        rootNavController.navigate(Destinations.CharacterDetail(id))
                    }
                )
            }
            composable<Destinations.Locations> {
                LocationsScreen(
                    onLocationClick = { id ->
                        rootNavController.navigate(Destinations.LocationDetail(id))
                    }
                )
            }
            composable<Destinations.Profile> {
                ProfileScreen(
                    onLogout = {
                        // ahora usamos el rootNavController para salir al login
                        rootNavController.navigate(Destinations.Login) {
                            popUpTo(Destinations.Main) { inclusive = true }
                        }
                    },
                    dataStore = DataStorePreferences(activity),
                    navController = nestedNavController,
                    activity = activity
                )
            }
        }
    }

    BackHandler {
        if (!nestedNavController.popBackStack()) {
            activity.finishAffinity()
        }
    }
}
