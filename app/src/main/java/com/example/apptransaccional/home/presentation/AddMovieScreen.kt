/*package com.example.apptransaccional.home.presentation

import androidx.compose.foundation.layout.*
import androidx.compose.material3.Button
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Slider
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.apptransaccional.home.data.models.Movie
import com.example.apptransaccional.home.data.models.MovieRequest

@Composable
fun AddMovieScreen(onMovieAdded: () -> Unit) {
    var title by remember { mutableStateOf("") }
    var year by remember { mutableStateOf("") }
    var category by remember { mutableStateOf("") }
    var stars by remember { mutableStateOf(1) }

    Column(modifier = Modifier.padding(16.dp)) {
        OutlinedTextField(value = title, onValueChange = { title = it }, label = { Text("Título") })
        OutlinedTextField(value = year, onValueChange = { year = it }, label = { Text("Año") })
        OutlinedTextField(value = category, onValueChange = { category = it }, label = { Text("Categoría") })
        Slider(value = stars.toFloat(), onValueChange = { stars = it.toInt() }, valueRange = 1f..5f)

        Button(
            onClick = {
                val newMovie = MovieRequest(title, year.toInt(), category, stars)
                viewModel.addMovie(newMovie)
                onMovieAdded()
            }
        ) {
            Text("Agregar Película")
        }
    }
}*/