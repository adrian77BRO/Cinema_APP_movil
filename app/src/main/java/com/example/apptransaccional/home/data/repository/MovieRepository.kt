package com.example.apptransaccional.home.domain

import android.content.Context
import com.example.apptransaccional.core.network.RetrofitHelper
import com.example.apptransaccional.home.data.models.MovieDTO
import com.example.apptransaccional.home.data.models.MovieRequest
import com.example.apptransaccional.home.data.models.MoviesDTO
import retrofit2.Response

class MovieRepository(context: Context) {
    private val sharedPreferences = context.getSharedPreferences("user_prefs", Context.MODE_PRIVATE)
    private val token: String = sharedPreferences.getString("token", "") ?: ""

    suspend fun getMovies(): Response<MoviesDTO> {
        return RetrofitHelper.movieService.getAllMovies(token)
    }

    suspend fun searchMoviesByTitle(title: String): Response<MoviesDTO> {
        return RetrofitHelper.movieService.getMoviesByTitle(token, title)
    }

    suspend fun createMovie(movieRequest: MovieRequest): Response<MovieDTO> {
        return RetrofitHelper.movieService.createMovie(token, movieRequest)
    }
}