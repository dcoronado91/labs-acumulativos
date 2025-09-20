package com.example.lab8moviles.ui.profile

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.foundation.shape.RoundedCornerShape
import com.example.lab8moviles.R

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProfileScreen(onLogout: () -> Unit) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Profile") },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primary,
                    titleContentColor = MaterialTheme.colorScheme.onPrimary
                )
            )
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .padding(padding)
                .fillMaxSize()
                .padding(24.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            // Profile Image (Replace with your image URL or resource)
            // To change the image:
            // 1. Add your image to res/drawable (e.g., my_profile_image.png)
            // 2. Replace painterResource(id = R.drawable.my_profile_image) with your image name
            Image(
                painter = painterResource(id = R.drawable.my_profile_image),
                contentDescription = "Profile Image",
                modifier = Modifier
                    .size(160.dp)
                    .clip(CircleShape)
            )
            Spacer(Modifier.height(24.dp))
            Text("Nombre: Derek Friedhelm Coronado Chilin", style = MaterialTheme.typography.bodyLarge)
            Spacer(Modifier.height(8.dp))
            Text("Carné: 24732", style = MaterialTheme.typography.bodyLarge)
            Spacer(Modifier.height(24.dp))
            Button(
                onClick = {
                    onLogout()
                },
                shape = RoundedCornerShape(50),
                modifier = Modifier.width(220.dp).height(48.dp)
            ) {
                Text("Cerrar Sesión")
            }
        }
    }
}