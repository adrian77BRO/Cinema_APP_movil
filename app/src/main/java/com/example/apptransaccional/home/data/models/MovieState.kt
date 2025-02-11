package com.example.apptransaccional.home.data.models

sealed class MovieState {
    data class Success(val movies: List<Movie>) : MovieState()
    data class Error(val message: String) : MovieState()
    object Loading : MovieState()
}