package com.example.apptransaccional.core.navigation

import androidx.compose.runtime.Composable
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.apptransaccional.login.presentation.LoginScreen
import com.example.apptransaccional.register.presentation.RegisterScreen
import com.example.apptransaccional.home.presentation.views.AddMovieScreen
import com.example.apptransaccional.home.presentation.views.HomeScreen
import com.example.apptransaccional.home.presentation.viewmodel.HomeViewModel
import com.example.apptransaccional.home.presentation.views.MovieListScreen
import com.example.apptransaccional.home.presentation.views.ProfileScreen
import com.example.apptransaccional.home.presentation.viewmodel.ProfileViewModel

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
        composable("profile/{username}") { backStackEntry ->
            val username = backStackEntry.arguments?.getString("username") ?: "Usuario"
            val profileViewModel: ProfileViewModel = viewModel()
            ProfileScreen(username, profileViewModel)
        }
    }
}