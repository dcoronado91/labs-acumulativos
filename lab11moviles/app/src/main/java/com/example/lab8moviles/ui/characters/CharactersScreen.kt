package com.example.lab8moviles.ui.characters

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
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
import coil.compose.AsyncImage
import com.example.lab8moviles.data.Character
import androidx.lifecycle.viewmodel.compose.viewModel

//Derek Friedhelm Coronado Chilin - 24732

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CharactersScreen(onCharacterClick: (Int) -> Unit) {
    val viewModel = viewModel<CharactersViewModel>()
    val state = viewModel.state.collectAsState().value

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Characters") },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primary,
                    titleContentColor = MaterialTheme.colorScheme.onPrimary
                )
            )
        }
    ) { padding ->
        if (state.isLoading) {
            Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    CircularProgressIndicator()
                    Spacer(modifier = Modifier.height(8.dp))
                    Text("Cargando")
                }
            }
        } else if (state.hasError) {
            Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    Icon(
                        imageVector = Icons.Filled.ErrorOutline,
                        contentDescription = null,
                        tint = MaterialTheme.colorScheme.error
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    Text("Error al obtener listado de personajes.")
                    Spacer(modifier = Modifier.height(8.dp))
                    Button(onClick = { viewModel.fetchData() }) {
                        Text("Reintentar")
                    }
                }
            }
        } else {
            LazyColumn(modifier = Modifier.padding(padding)) {
                items(state.data) { c ->
                    CharacterListItem(character = c, onClick = onCharacterClick)
                }
            }
        }
    }
}

@Composable
fun CharacterListItem(character: Character, onClick: (Int) -> Unit) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onClick(character.id) }
            .padding(horizontal = 16.dp, vertical = 12.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        CharacterImage(url = character.image, name = character.name, size = 56.dp)
        Spacer(modifier = Modifier.width(12.dp))
        Column {
            Text(character.name, fontWeight = FontWeight.Bold)
            Text("${character.species} - ${character.status}", style = MaterialTheme.typography.bodySmall)
        }
    }
}

@Composable
fun CharacterImage(url: String, name: String, size: androidx.compose.ui.unit.Dp) {
    if (url.isNotBlank()) {
        AsyncImage(
            model = url,
            contentDescription = name,
            contentScale = ContentScale.Crop,
            modifier = Modifier.size(size).clip(CircleShape)
        )
    } else {
        Box(
            modifier = Modifier.size(size).clip(CircleShape).background(MaterialTheme.colorScheme.primary.copy(alpha = 0.2f)),
            contentAlignment = Alignment.Center
        ) {
            Text(text = name.firstOrNull()?.toString() ?: "?", fontWeight = FontWeight.Bold)
        }
    }
}