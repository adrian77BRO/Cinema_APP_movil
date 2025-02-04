package com.example.apptransaccional.home.data.datasource

import com.example.apptransaccional.core.network.RetrofitClient
import com.example.apptransaccional.home.data.models.MovieDTO
import com.example.apptransaccional.home.data.models.MovieRequest
import com.example.apptransaccional.home.data.models.MoviesDTO
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST
import retrofit2.http.Path

interface MovieService {
    @GET("movies")
    suspend fun getAllMovies(@Header("Authorization") token: String): Response<MoviesDTO>

    @GET("movies/mov/{title}")
    suspend fun getMoviesByTitle(@Header("Authorization") token: String, @Path("title") title: String): Response<MoviesDTO>

    @POST("movies")
    suspend fun createMovie(@Header("Authorization") token: String, @Body movie: MovieRequest): Response<MovieDTO>

    companion object {
        val api: MovieService by lazy {
            RetrofitClient.instance.create(MovieService::class.java)
        }
    }
}