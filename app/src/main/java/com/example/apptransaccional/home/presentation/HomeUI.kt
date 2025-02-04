package com.example.apptransaccional.home.presentation

import android.widget.Toast
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.example.apptransaccional.home.data.models.Movie
import com.example.apptransaccional.home.data.models.MovieRequest
import androidx.lifecycle.viewmodel.compose.viewModel

@Composable
fun HomeScreen(username: String) {
    val movieViewModel: MovieViewModel = viewModel()
    val movieState by movieViewModel.movieState.observeAsState(MovieState.Loading)

    var title by remember { mutableStateOf("") }
    var year by remember { mutableStateOf("") }
    var category by remember { mutableStateOf("") }
    var stars by remember { mutableStateOf("") }
    var searchQuery by remember { mutableStateOf("") }

    // Cargar las películas al iniciar
    LaunchedEffect(Unit) {
        movieViewModel.getMovies()
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        Text(text = "Bienvenido, $username", style = MaterialTheme.typography.headlineMedium, color = Color.White)

        Spacer(modifier = Modifier.height(16.dp))

        // Barra de búsqueda
        BasicTextField(
            value = searchQuery,
            onValueChange = {
                searchQuery = it
                movieViewModel.searchMoviesByTitle(it)
            },
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp)
        )

        Spacer(modifier = Modifier.height(8.dp))

        // Mostrar el estado de las películas
        when (movieState) {
            is MovieState.Loading -> {
                CircularProgressIndicator(modifier = Modifier.align(Alignment.CenterHorizontally))
            }
            is MovieState.Error -> {
                Text(text = "Error: ${(movieState as MovieState.Error).message}", color = Color.Red)
            }
            is MovieState.Success -> {
                val movies = (movieState as MovieState.Success).movies
                LazyColumn {
                    items(movies) { movie ->
                        Text(text = "${movie.title} (${movie.year}) - ${movie.category} - ${movie.stars}")
                    }
                }
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Formulario para agregar una nueva película
        TextField(
            value = title,
            onValueChange = { title = it },
            label = { Text("Título") },
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(modifier = Modifier.height(8.dp))

        TextField(
            value = year,
            onValueChange = { year = it },
            label = { Text("Año") },
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(modifier = Modifier.height(8.dp))

        TextField(
            value = category,
            onValueChange = { category = it },
            label = { Text("Categoría") },
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(modifier = Modifier.height(8.dp))

        TextField(
            value = stars,
            onValueChange = { stars = it },
            label = { Text("Calificación (1-5)") },
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(modifier = Modifier.height(16.dp))

        Button(
            onClick = {
                val movieRequest = MovieRequest(
                    title = title,
                    year = year.toIntOrNull() ?: 0,
                    category = category,
                    stars = stars.toIntOrNull() ?: 1
                )
                movieViewModel.createMovie(movieRequest)
            },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Agregar Película")
        }
    }
}

/*import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.example.apptransaccional.home.data.models.Movie

@Composable
fun HomeScreen(username: String, viewModel: HomeViewModel) {
    var searchQuery by remember { mutableStateOf("") }
    val movies by viewModel.movies.observeAsState(emptyList())

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        Text(
            text = "Bienvenido, $username",
            style = MaterialTheme.typography.headlineMedium,
            color = Color.White
        )

        Spacer(modifier = Modifier.height(16.dp))

        // Buscar película
        OutlinedTextField(
            value = searchQuery,
            onValueChange = { searchQuery = it },
            label = { Text("Buscar película") },
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(16.dp))

        Button(
            onClick = { viewModel.searchMovieByTitle(searchQuery) },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Buscar")
        }

        Spacer(modifier = Modifier.height(16.dp))

        Button(
            onClick = { /* navegar a pantalla para agregar película */ },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Agregar película")
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Lista de películas
        LazyColumn {
            items(movies) { movie ->
                MovieRow(movie)
            }
        }
    }
}

@Composable
fun MovieRow(movie: Movie) {
    Row(modifier = Modifier.fillMaxWidth()) {
        Text(text = movie.title, modifier = Modifier.weight(1f))
        Text(text = movie.year.toString(), modifier = Modifier.weight(1f))
        Text(text = movie.category, modifier = Modifier.weight(1f))
        Text(text = movie.stars.toString(), modifier = Modifier.weight(1f))
    }
}*/


/*@Composable
fun HomeScreen(username: String) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Black),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = "Bienvenido, $username",
            style = MaterialTheme.typography.headlineMedium,
            color = Color.White
        )
    }
}*/
