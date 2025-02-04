package com.example.apptransaccional.core.navigation

import androidx.compose.runtime.Composable
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.app.ui.LoginScreen
import com.example.app.ui.RegisterScreen
import com.example.apptransaccional.home.presentation.AddMovieScreen
import com.example.apptransaccional.home.presentation.HomeScreen
import com.example.apptransaccional.home.presentation.HomeViewModel
import com.example.apptransaccional.home.presentation.MovieListScreen

@Composable
fun NavigationGraph(navController: NavHostController) {
    NavHost(navController, startDestination = "login") {
        composable("login") {
            LoginScreen(
                onLoginSuccess = { username ->
                    navController.navigate("home/$username") {
                        popUpTo("login") { inclusive = true }
                    }
                },
                onNavigateToRegister = { navController.navigate("register") }
            )
        }
        composable("register") {
            RegisterScreen(
                onRegisterSuccess = { /*navController.navigate("login")*/ },
                onNavigateToLogin = { navController.navigate("login") }
            )
        }
        composable("home/{username}") { backStackEntry ->
            val username = backStackEntry.arguments?.getString("username") ?: "Usuario"
            HomeScreen(username, navController)
        }
        composable("movies") {
            val movieViewModel: HomeViewModel = viewModel()
            MovieListScreen(navController, movieViewModel)
        }
        composable("addMovie") {
            val movieViewModel: HomeViewModel = viewModel()
            AddMovieScreen(navController, movieViewModel)
        }
    }
}