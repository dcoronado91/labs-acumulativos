package com.example.lab8moviles.ui.locations

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.ErrorOutline
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.lifecycle.ViewModel
import androidx.navigation.NavBackStackEntry
import com.example.lab8moviles.data.AppDatabase
import com.example.lab8moviles.navigation.Destinations
import androidx.navigation.toRoute
import androidx.lifecycle.viewmodel.compose.viewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LocationDetailScreen(
    onBack: () -> Unit,
    navBackStackEntry: NavBackStackEntry,
    db: AppDatabase
) {
    val route = navBackStackEntry.toRoute<Destinations.LocationDetail>()
    val viewModel: LocationDetailViewModel = viewModel(
        factory = LocationDetailViewModelFactory(db, route.locationId)
    )
    val state = viewModel.state.collectAsState().value

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Location Detail") },
                navigationIcon = {
                    IconButton(onClick = onBack) {
                        Icon(Icons.Filled.ArrowBack, contentDescription = "Back")
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primary,
                    titleContentColor = MaterialTheme.colorScheme.onPrimary,
                    navigationIconContentColor = MaterialTheme.colorScheme.onPrimary
                )
            )
        }
    ) { padding ->
        if (state.isLoading) {
            Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                CircularProgressIndicator()
            }
        } else if (state.hasError || state.data == null) {
            Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    Icon(
                        imageVector = Icons.Filled.ErrorOutline,
                        contentDescription = null,
                        tint = MaterialTheme.colorScheme.error
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    Text("Error al obtener detalles de la ubicación.")
                    Spacer(modifier = Modifier.height(8.dp))
                    Button(onClick = { viewModel.fetchData() }) {
                        Text("Reintentar")
                    }
                }
            }
        } else {
            val location = state.data
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(padding)
                    .padding(16.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Box(
                    modifier = Modifier
                        .size(200.dp)
                        .clip(CircleShape)
                        .background(MaterialTheme.colorScheme.primary.copy(alpha = 0.2f)),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = location.name.firstOrNull()?.toString() ?: "?",
                        style = MaterialTheme.typography.headlineMedium,
                        fontWeight = FontWeight.Bold
                    )
                }
                Spacer(modifier = Modifier.height(16.dp))
                Text(location.name, style = MaterialTheme.typography.headlineMedium, fontWeight = FontWeight.Bold)
                Text("Type: ${location.type}", style = MaterialTheme.typography.bodyLarge)
                Text("Dimension: ${location.dimension}", style = MaterialTheme.typography.bodyLarge)
                Text("Residents: ${location.residents.size} characters", style = MaterialTheme.typography.bodyLarge)
            }
        }
    }
}

class LocationDetailViewModelFactory(private val db: AppDatabase, private val id: Int) : androidx.lifecycle.ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(LocationDetailViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return LocationDetailViewModel(db, id) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}