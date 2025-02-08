package com.example.apptransaccional.home.presentation.views

import android.widget.Toast
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.outlined.Star
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.apptransaccional.home.data.models.MovieRequest
import com.example.apptransaccional.home.presentation.viewmodel.HomeViewModel

@Composable
fun AddMovieScreen(navController: NavController, movieViewModel: HomeViewModel) {
    var title by remember { mutableStateOf("") }
    var year by remember { mutableStateOf("") }
    var category by remember { mutableStateOf("") }
    var stars by remember { mutableStateOf(0) }
    val context = LocalContext.current

    Scaffold(
        containerColor = Color(0xFF121212),
        floatingActionButton = {
            FloatingActionButton(
                onClick = { navController.navigate("movies") },
                containerColor = Color(0xFF6A5ACD)
            ) {
                Icon(Icons.Default.Close, contentDescription = "Atrás", tint = Color.White)
            }
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(20.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = "Agregar nueva película",
                color = Color.White,
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.align(Alignment.CenterHorizontally)
            )
            Spacer(modifier = Modifier.height(16.dp))

            CustomTextField("Título", title) { title = it }
            CustomTextField("Año", year, keyboardType = KeyboardType.Number) { year = it }
            CustomTextField("Categoría", category) { category = it }
            //CustomTextField("Calificación (1-5)", stars, keyboardType = KeyboardType.Number) { stars = it }

            Spacer(modifier = Modifier.height(16.dp))

            Text(text = "Calificación:", color = Color.White, fontSize = 18.sp)
            Row(
                modifier = Modifier.padding(vertical = 8.dp),
                horizontalArrangement = Arrangement.Center
            ) {
                for (i in 1..5) {
                    IconButton(onClick = { stars = i }) {
                        Icon(
                            imageVector = if (i <= stars) Icons.Filled.Star else Icons.Outlined.Star,
                            contentDescription = "Calificación $i estrellas",
                            tint = if (i <= stars) Color.Yellow else Color.White,
                            modifier = Modifier.size(40.dp)
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(16.dp))
            Button(
                onClick = {
                    if (title.isBlank() || year.isBlank() || category.isBlank() || stars == 0) {
                        Toast.makeText(context, "Todos los campos son obligatorios", Toast.LENGTH_SHORT).show()
                    } else {
                        val movieRequest = MovieRequest(
                            title,
                            year.toIntOrNull() ?: 0,
                            category,
                            stars
                        )
                        movieViewModel.createMovie(movieRequest)
                    }
                },
                colors = ButtonDefaults.buttonColors(Color.Blue),
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Guardar película", color = Color.White)
            }
        }
    }
}

@Composable
fun CustomTextField(label: String, value: String, keyboardType: KeyboardType = KeyboardType.Text, onValueChange: (String) -> Unit) {
    OutlinedTextField(
        value = value,
        onValueChange = onValueChange,
        label = { Text(label) },
        keyboardOptions = KeyboardOptions.Default.copy(keyboardType = keyboardType),
        colors = TextFieldDefaults.colors(
            focusedContainerColor = Color.Black,
            unfocusedContainerColor = Color.Black,
            focusedTextColor = Color.White,
            unfocusedTextColor = Color.White,
            focusedIndicatorColor = Color.Blue,
            unfocusedIndicatorColor = Color.Blue
        ),
        modifier = Modifier.fillMaxWidth().padding(vertical = 8.dp)
    )
}
