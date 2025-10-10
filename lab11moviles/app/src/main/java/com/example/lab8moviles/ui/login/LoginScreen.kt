package com.example.lab8moviles.ui.login

import android.app.Activity
import android.util.Log
import androidx.activity.compose.BackHandler
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import coil.compose.AsyncImage
import com.example.lab8moviles.data.AppDatabase
import com.example.lab8moviles.data.DataStorePreferences
import com.example.lab8moviles.data.CharacterDb
import com.example.lab8moviles.data.LocationDb
import com.example.lab8moviles.data.toEntity
import com.example.lab8moviles.navigation.Destinations
import kotlinx.coroutines.launch
import kotlinx.coroutines.delay

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LoginScreen(navController: NavHostController, dataStore: DataStorePreferences, activity: Activity) {
    var name by remember { mutableStateOf(TextFieldValue("")) }
    var isSyncing by remember { mutableStateOf(false) }
    val scope = rememberCoroutineScope()
    var errorMessage by remember { mutableStateOf<String?>(null) }

    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            AsyncImage(
                model = coil.request.ImageRequest.Builder(LocalContext.current)
                    .data("https://pngimg.com/d/rick_morty_PNG40.png")
                    .crossfade(true)
                    .build(),
                contentDescription = "Rick and Morty Logo",
                modifier = Modifier.size(300.dp),
                contentScale = ContentScale.Fit,
                onError = {
                    Log.e("LoginScreen", "Failed to load logo: ${it.result.throwable.message}")
                }
            )
            Spacer(modifier = Modifier.height(16.dp))
            OutlinedTextField(
                value = name,
                onValueChange = { name = it },
                label = { Text("Ingresa tu nombre") },
                modifier = Modifier.fillMaxWidth(0.8f),
                enabled = !isSyncing
            )
            Spacer(modifier = Modifier.height(16.dp))
            Button(
                onClick = {
                    if (name.text.isNotBlank()) {
                        isSyncing = true
                        scope.launch {
                            try {
                                Log.d("LoginScreen", "Starting data sync")
                                delay(4000)
                                val db = AppDatabase.getDatabase(activity)
                                val characterDb = CharacterDb()
                                val locationDb = LocationDb()
                                db.characterDao().insertAll(characterDb.getAllCharacters().map { it.toEntity() })
                                db.locationDao().insertAll(locationDb.getAllLocations().map { it.toEntity() })
                                Log.d("LoginScreen", "Data synced to Room")
                                dataStore.saveName(name.text)
                                Log.d("LoginScreen", "Name saved to DataStore: ${name.text}")
                                isSyncing = false
                                navController.navigate(Destinations.Main) {
                                    popUpTo(navController.graph.startDestinationId) { inclusive = true }
                                }
                            } catch (e: Exception) {
                                Log.e("LoginScreen", "Error during sync: ${e.message}", e)
                                isSyncing = false
                                errorMessage = "Error syncing data: ${e.message}"
                            }
                        }
                    } else {
                        errorMessage = "Please enter a name"
                    }
                },
                enabled = name.text.isNotBlank() && !isSyncing
            ) {
                if (isSyncing) {
                    CircularProgressIndicator(modifier = Modifier.size(24.dp))
                } else {
                    Text("Iniciar Sesión")
                }
            }
            errorMessage?.let {
                Spacer(modifier = Modifier.height(8.dp))
                Text(it, color = MaterialTheme.colorScheme.error)
            }
        }
    }

    BackHandler {
        activity.finishAffinity()
    }
}