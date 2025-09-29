package com.example.lab8moviles.ui.characterdetail

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.example.lab8moviles.data.CharacterDb

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CharacterDetailScreen(characterId: Int, onBack: () -> Unit) {
    val db = CharacterDb()
    val character = db.getCharacterById(characterId)

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Character Detail") },
                navigationIcon = {
                    IconButton(onClick = onBack) {
                        Icon(painter = painterResource(android.R.drawable.ic_menu_revert), contentDescription = "Back")
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
        Column(
            modifier = Modifier
                .padding(padding)
                .fillMaxSize()
                .padding(24.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Spacer(Modifier.height(12.dp))
            if (character.image.isNotBlank()) {
                AsyncImage(
                    model = character.image,
                    contentDescription = character.name,
                    modifier = Modifier.size(160.dp).clip(CircleShape),
                    contentScale = ContentScale.Crop
                )
            } else {
                Box(
                    modifier = Modifier.size(160.dp).clip(CircleShape).background(MaterialTheme.colorScheme.primary.copy(alpha = 0.2f)),
                    contentAlignment = Alignment.Center
                ) {
                    Text(character.name.firstOrNull()?.toString() ?: "?", fontSize = 36.sp)
                }
            }
            Spacer(Modifier.height(12.dp))
            Text(character.name, fontWeight = FontWeight.SemiBold, fontSize = 20.sp)
            Spacer(Modifier.height(24.dp))
            Column(modifier = Modifier.fillMaxWidth().padding(horizontal = 8.dp)) {
                InfoRow(label = "Species:", value = character.species)
                InfoRow(label = "Status:", value = character.status)
                InfoRow(label = "Gender:", value = character.gender)
            }
        }
    }
}

@Composable
fun InfoRow(label: String, value: String) {
    Row(modifier = Modifier.fillMaxWidth().padding(vertical = 10.dp)) {
        Text(label, modifier = Modifier.weight(1f), style = MaterialTheme.typography.bodySmall)
        Text(value, modifier = Modifier.weight(1f), textAlign = TextAlign.End, style = MaterialTheme.typography.bodySmall)
    }
}