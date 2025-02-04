package com.example.apptransaccional.home.presentation

import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController

@Composable
fun HomeScreen(username: String, navController: NavController) {
    Scaffold(
        containerColor = Color(0xFF121212),
        floatingActionButton = {
            FloatingActionButton(
                onClick = { navController.navigate("movies") },
                containerColor = Color(0xFF6A5ACD)
            ) {
                Icon(Icons.Default.Star, contentDescription = "Películas", tint = Color.White)
            }
        }
    ) { padding ->
        Column(
            modifier = Modifier.fillMaxSize().padding(padding),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Text(text = "Bienvenido, $username", color = Color.White, fontSize = 24.sp)
        }
    }
}