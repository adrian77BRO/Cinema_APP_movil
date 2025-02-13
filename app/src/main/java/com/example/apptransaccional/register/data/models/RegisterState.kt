package com.example.apptransaccional.register.data.models

sealed class RegisterState {
    data class Success(val message: String) : RegisterState()
    data class Error(val message: String) : RegisterState()
}