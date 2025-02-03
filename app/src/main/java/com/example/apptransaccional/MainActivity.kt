package com.example.apptransaccional

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.navigation.compose.rememberNavController
import com.example.apptransaccional.core.navigation.NavigationGraph
import com.example.apptransaccional.ui.theme.AppTransaccionalTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            AppTransaccionalTheme {
                val navController = rememberNavController()
                NavigationGraph(navController)
            }
        }
    }
}
