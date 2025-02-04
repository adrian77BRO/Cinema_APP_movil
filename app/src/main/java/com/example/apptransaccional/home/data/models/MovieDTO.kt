package com.example.apptransaccional.home.data.models

data class MovieDTO(
    val message: String,
    val status: String,
    val movie: MovieRequest?,
)

data class MoviesDTO(
    val status: String,
    val message: String,
    val movies: List<Movie>
)
