package com.example.lab8moviles.ui.login

import androidx.annotation.DrawableRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LoginScreen(onStart: () -> Unit, @DrawableRes logoRes: Int? = null, logoUrl: String? = null) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("") },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primary,
                    titleContentColor = MaterialTheme.colorScheme.onPrimary
                )
            )
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .padding(24.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.SpaceBetween
        ) {
            Spacer(Modifier.height(12.dp))
            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                Spacer(Modifier.height(60.dp))
                if (logoRes != null) {
                    Image(
                        painter = painterResource(id = logoRes),
                        contentDescription = "Logo",
                        modifier = Modifier.size(220.dp)
                    )
                } else if (!logoUrl.isNullOrBlank()) {
                    AsyncImage(model = logoUrl, contentDescription = "Logo", modifier = Modifier.size(220.dp))
                } else {
                    Box(
                        modifier = Modifier.size(220.dp).clip(RoundedCornerShape(16.dp)).background(Color.LightGray),
                        contentAlignment = Alignment.Center
                    ) {
                        Text("Logo aquí", textAlign = TextAlign.Center)
                    }
                }
                Spacer(Modifier.height(24.dp))
                Button(
                    onClick = onStart,
                    shape = RoundedCornerShape(50),
                    modifier = Modifier.width(220.dp).height(48.dp)
                ) {
                    Text(text = "Entrar")
                }
            }
            Text(
                text = "Derek Friedhelm Coronado Chilin - 24732",
                style = MaterialTheme.typography.bodySmall,
                modifier = Modifier.padding(bottom = 8.dp)
            )
        }
    }
}