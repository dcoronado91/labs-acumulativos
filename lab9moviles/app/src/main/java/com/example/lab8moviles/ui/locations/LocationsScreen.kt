package com.example.lab8moviles.ui.locations

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.lab8moviles.data.Location
import com.example.lab8moviles.data.LocationDb

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LocationsScreen(onLocationClick: (Int) -> Unit) {
    val db = LocationDb()
    val locations = db.getAllLocations()

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Locations") },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primary,
                    titleContentColor = MaterialTheme.colorScheme.onPrimary
                )
            )
        }
    ) { padding ->
        LazyColumn(modifier = Modifier.padding(padding)) {
            items(locations) { location ->
                LocationListItem(location = location, onClick = onLocationClick)
            }
        }
    }
}

@Composable
fun LocationListItem(location: Location, onClick: (Int) -> Unit) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onClick(location.id) }
            .padding(horizontal = 16.dp, vertical = 12.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Column {
            Text(location.name, style = MaterialTheme.typography.bodyLarge)
            Text(location.type, style = MaterialTheme.typography.bodySmall)
        }
    }
}