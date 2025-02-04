/*package com.example.apptransaccional.home.domain

import com.example.apptransaccional.core.network.RetrofitClient
import com.example.apptransaccional.home.data.datasource.MovieService
import com.example.apptransaccional.home.data.models.Movie
import com.example.apptransaccional.home.data.models.MovieRequest
import com.example.apptransaccional.register.data.datasource.RegisterService
import com.example.apptransaccional.register.data.models.RegisterRequest

class MovieRepository {
    private val movieService = MovieService.api

    suspend fun getAllMovies(): List<Movie>? {
        val response = movieService.getAllMovies()
        return if (response.isSuccessful) response.body() else null
    }

    suspend fun getMoviesByTitle(title: String): List<Movie>? {
        val response = movieService.getMoviesByTitle(title)
        return if (response.isSuccessful) response.body() else null
    }

    suspend fun createMovie(movie: MovieRequest): Movie? {
        val response = movieService.createMovie(movie)
        return if (response.isSuccessful) response.body() else null
    }
}*/
