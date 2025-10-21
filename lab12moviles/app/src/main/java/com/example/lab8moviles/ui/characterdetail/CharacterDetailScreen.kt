package com.example.lab8moviles.ui.characterdetail

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.ErrorOutline
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.lifecycle.ViewModel
import androidx.navigation.NavBackStackEntry
import coil.compose.AsyncImage
import androidx.navigation.toRoute
import com.example.lab8moviles.data.AppDatabase
import com.example.lab8moviles.navigation.Destinations
import androidx.lifecycle.viewmodel.compose.viewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CharacterDetailScreen(
    onBack: () -> Unit,
    navBackStackEntry: NavBackStackEntry,
    db: AppDatabase
) {
    val route = navBackStackEntry.toRoute<Destinations.CharacterDetail>()
    val viewModel: CharacterDetailViewModel = viewModel(
        factory = CharacterDetailViewModelFactory(db, route.characterId)
    )
    val state = viewModel.state.collectAsState().value

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Character Detail") },
                navigationIcon = {
                    IconButton(onClick = onBack) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Back")
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
                    Text("Error al obtener detalles del personaje.")
                    Spacer(modifier = Modifier.height(8.dp))
                    Button(onClick = { viewModel.fetchData() }) {
                        Text("Reintentar")
                    }
                }
            }
        } else {
            val character = state.data
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(padding)
                    .padding(16.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                AsyncImage(
                    model = character.image,
                    contentDescription = character.name,
                    contentScale = ContentScale.Crop,
                    modifier = Modifier
                        .size(200.dp)
                        .clip(CircleShape)
                )
                Spacer(modifier = Modifier.height(16.dp))
                Text(character.name, style = MaterialTheme.typography.headlineMedium, fontWeight = FontWeight.Bold)
                Text("Status: ${character.status}", style = MaterialTheme.typography.bodyLarge)
                Text("Species: ${character.species}", style = MaterialTheme.typography.bodyLarge)
                Text("Gender: ${character.gender}", style = MaterialTheme.typography.bodyLarge)
                Text("Origin: ${character.originName}", style = MaterialTheme.typography.bodyLarge)
                Text("Location: ${character.locationName}", style = MaterialTheme.typography.bodyLarge)
            }
        }
    }
}

class CharacterDetailViewModelFactory(private val db: AppDatabase, private val id: Int) : androidx.lifecycle.ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(CharacterDetailViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return CharacterDetailViewModel(db, id) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}