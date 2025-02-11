package com.example.apptransaccional.login.data.models

sealed class LoginState {
    data class Success(val username: String, val token: String) : LoginState()
    data class Error(val message: String) : LoginState()
}