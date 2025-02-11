package com.example.apptransaccional.home.presentation.views

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.apptransaccional.home.data.models.Movie
import androidx.navigation.NavController
import com.example.apptransaccional.R
import com.example.apptransaccional.home.data.models.MovieState
import com.example.apptransaccional.home.presentation.viewmodel.HomeViewModel

@Composable
fun MovieListScreen(navController: NavController, movieViewModel: HomeViewModel) {
    val movieState by movieViewModel.movieState.observeAsState()
    var searchQuery by remember { mutableStateOf("") }

    LaunchedEffect(Unit) {
        movieViewModel.getMovies()
    }

    Scaffold(
        floatingActionButton = {
            FloatingActionButton(
                onClick = { navController.navigate("addMovie") },
                containerColor = Color(0xFF6A5ACD)
            ) {
                Icon(Icons.Default.Add, contentDescription = "Agregar película", tint = Color.White)
            }
        },
        containerColor = Color(0xFF121212)
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
        ) {
            OutlinedTextField(
                value = searchQuery,
                onValueChange = { query ->
                    searchQuery = query
                    if (query.isNotEmpty()) {
                        movieViewModel.searchMoviesByTitle(query)
                    } else {
                        movieViewModel.getMovies()
                    }
                },
                label = { Text("Buscar película", color = Color.White) },
                textStyle = TextStyle(color = Color.White),
                leadingIcon = {
                    Icon(Icons.Default.Search, contentDescription = "Buscar", tint = Color.White)
                },
                colors = TextFieldDefaults.colors(
                    focusedContainerColor = Color.Black,
                    unfocusedContainerColor = Color.Black,
                    focusedTextColor = Color.White,
                    unfocusedTextColor = Color.White,
                    focusedIndicatorColor = Color.Blue,
                    unfocusedIndicatorColor = Color.Blue
                ),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp)
            )

            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                when (movieState) {
                    is MovieState.Loading -> CircularProgressIndicator()

                    is MovieState.Success -> {
                        val movies = (movieState as MovieState.Success).movies
                        if (movies.isEmpty()) {
                            Text(
                                text = "No hay películas registradas",
                                color = Color.White,
                                fontSize = 24.sp,
                                fontWeight = FontWeight.Bold
                            )
                        } else {
                            LazyColumn(
                                modifier = Modifier.fillMaxSize().padding(16.dp)
                            ) {
                                items(movies) { movie ->
                                    MovieCard(movie)
                                }
                            }
                        }
                    }

                    is MovieState.Error -> {
                        Text(
                            text = (movieState as MovieState.Error).message,
                            color = Color.Red,
                            fontSize = 20.sp,
                            fontWeight = FontWeight.Bold
                        )
                    }

                    else -> {}
                }
            }
        }
    }
}

@Composable
fun MovieCard(movie: Movie) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp),
        colors = CardDefaults.cardColors(containerColor = Color(0xFF1E1E1E)),
        shape = RoundedCornerShape(16.dp),
        elevation = CardDefaults.cardElevation(4.dp)
    ) {
        Row(modifier = Modifier.padding(16.dp), verticalAlignment = Alignment.CenterVertically) {
            Image(
                painter = painterResource(id = R.drawable.cinema),
                contentDescription = "Movie Image",
                modifier = Modifier
                    .size(80.dp)
                    .clip(RoundedCornerShape(8.dp))
            )
            Spacer(modifier = Modifier.width(16.dp))
            Column {
                Text(text = movie.title, color = Color.White, fontSize = 18.sp, fontWeight = FontWeight.Bold)
                Text(text = "Año: ${movie.year}", color = Color.Gray, fontSize = 14.sp)
                Text(text = "Categoría: ${movie.category}", color = Color.Gray, fontSize = 14.sp)
                Text(text = "Calificación: ${movie.stars}/5", color = Color(0xFFFFD700), fontSize = 14.sp)
            }
        }
    }
}