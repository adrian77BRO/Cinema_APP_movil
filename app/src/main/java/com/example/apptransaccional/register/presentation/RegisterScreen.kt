package com.example.apptransaccional.register.presentation

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.ClickableText
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.apptransaccional.register.data.models.RegisterState

@Composable
fun RegisterScreen(
    registerViewModel: RegisterViewModel = viewModel(),
    onNavigateToLogin: () -> Unit
) {
    var username by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    val registerState by registerViewModel.registerState.observeAsState()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Black)
            .padding(24.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text("Registro", style = MaterialTheme.typography.headlineMedium, color = Color.White)

        Spacer(modifier = Modifier.height(20.dp))

        OutlinedTextField(
            value = username,
            onValueChange = { username = it },
            label = { Text("Usuario") },
            modifier = Modifier.fillMaxWidth(),
            colors = TextFieldDefaults.colors(
                focusedContainerColor = Color.Black,
                unfocusedContainerColor = Color.Black,
                focusedTextColor = Color.White,
                unfocusedTextColor = Color.White,
                focusedIndicatorColor = Color.Blue,
                unfocusedIndicatorColor = Color.Blue,
            )
        )

        Spacer(modifier = Modifier.height(10.dp))

        OutlinedTextField(
            value = email,
            onValueChange = { email = it },
            label = { Text("Correo electrónico") },
            modifier = Modifier.fillMaxWidth(),
            colors = TextFieldDefaults.colors(
                focusedContainerColor = Color.Black,
                unfocusedContainerColor = Color.Black,
                focusedTextColor = Color.White,
                unfocusedTextColor = Color.White,
                focusedIndicatorColor = Color.Blue,
                unfocusedIndicatorColor = Color.Blue,
            )
        )

        Spacer(modifier = Modifier.height(10.dp))

        OutlinedTextField(
            value = password,
            onValueChange = { password = it },
            label = { Text("Contraseña") },
            visualTransformation = PasswordVisualTransformation(),
            modifier = Modifier.fillMaxWidth(),
            colors = TextFieldDefaults.colors(
                focusedContainerColor = Color.Black,
                unfocusedContainerColor = Color.Black,
                focusedTextColor = Color.White,
                unfocusedTextColor = Color.White,
                focusedIndicatorColor = Color.Blue,
                unfocusedIndicatorColor = Color.Blue,
            )
        )

        Spacer(modifier = Modifier.height(20.dp))

        Button(
            onClick = { registerViewModel.register(username, email, password) },
            modifier = Modifier.fillMaxWidth(),
            colors = ButtonDefaults.buttonColors(Color.Blue)
        ) {
            Text("Registrarse", color = Color.White)
        }

        registerState?.let { state ->
            Spacer(modifier = Modifier.height(10.dp))
            when (state) {
                is RegisterState.Success -> {
                    Text(text = state.message, color = Color.Green)
                    LaunchedEffect(Unit) {
                        kotlinx.coroutines.delay(2000)
                        onNavigateToLogin()
                        registerViewModel.clearState()
                    }
                }
                is RegisterState.Error -> {
                    Text(text = state.message, color = Color.Red)
                }
            }
        }

        Spacer(modifier = Modifier.height(20.dp))

        ClickableText(
            text = AnnotatedString("¿Ya tienes cuenta? Inicia sesión aquí"),
            onClick = { onNavigateToLogin() },
            style = MaterialTheme.typography.bodyMedium.copy(color = Color.Blue)
        )
    }
}