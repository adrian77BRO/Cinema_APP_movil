package com.example.apptransaccional.core.navigation

/*import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.apptransaccional.login.presentation.LoginScreen
import com.example.apptransaccional.login.presentation.LoginViewModel
import com.example.apptransaccional.register.presentation.RegisterScreen
import com.example.apptransaccional.register.presentation.RegisterViewModel

@Composable
fun NavigationGraph() {
    val navController = rememberNavController()

    NavHost(navController = navController, startDestination = "login") {
        composable("login") {
            LoginScreen(viewModel = LoginViewModel(), navController)
        }
        composable("register") {
            RegisterScreen(viewModel = RegisterViewModel(), navController)
        }
    }
}*/

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.app.ui.LoginScreen
import com.example.app.ui.RegisterScreen

@Composable
fun NavigationGraph(navController: NavHostController) {
    NavHost(navController, startDestination = "login") {
        composable("login") {
            LoginScreen(
                onLoginSuccess = { /* Aquí puedes navegar a otra pantalla si deseas */ },
                onNavigateToRegister = { navController.navigate("register") }
            )
        }
        composable("register") {
            RegisterScreen(
                onRegisterSuccess = { /* Aquí puedes navegar a otra pantalla si deseas */ },
                onNavigateToLogin = { navController.navigate("login") }
            )
        }
    }
}
