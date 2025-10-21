package com.example.lab8moviles.ui.profile

import android.app.Activity
import android.util.Log
import androidx.activity.compose.BackHandler
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.Logout
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.example.lab8moviles.data.DataStorePreferences
import com.example.lab8moviles.navigation.Destinations
import kotlinx.coroutines.launch
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.res.painterResource
import com.example.lab8moviles.R

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProfileScreen(onLogout: () -> Unit, dataStore: DataStorePreferences, navController: NavHostController, activity: Activity) {
    val name by dataStore.nameFlow.collectAsState(initial = null)
    val scope = rememberCoroutineScope()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Image(
            painter = painterResource(id = R.drawable.my_profile_image), // 👈 tu imagen aquí
            contentDescription = "Imagen de perfil",
            modifier = Modifier
                .size(150.dp)
                .padding(bottom = 16.dp)
        )
        Text("Bienvenido, ${name ?: "User"}!", style = MaterialTheme.typography.headlineMedium)
        Spacer(modifier = Modifier.height(24.dp))
        Button(onClick = {
            scope.launch {
                try {
                    dataStore.clearName()
                    Log.d("ProfileScreen", "Cleared name, navigating to login")
                    onLogout()
                } catch (e: Exception) {
                    Log.e("ProfileScreen", "Error on logout: ${e.message}", e)
                }
            }
        }) {
            Icon(Icons.AutoMirrored.Filled.Logout, contentDescription = "Logout")
            Spacer(modifier = Modifier.width(8.dp))
            Text("Cerrar Sesión")
        }
    }

    BackHandler {
        navController.navigate(Destinations.Characters)
    }
}