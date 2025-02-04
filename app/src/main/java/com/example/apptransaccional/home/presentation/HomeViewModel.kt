package com.example.apptransaccional.home.presentation

import android.app.Application
import android.content.Context
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.apptransaccional.home.data.datasource.MovieService
import com.example.apptransaccional.home.data.models.Movie
import com.example.apptransaccional.home.data.models.MovieDTO
import com.example.apptransaccional.home.data.models.MovieRequest
import com.example.apptransaccional.home.data.models.MoviesDTO
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.Response

sealed class MovieState {
    data class Success(val movies: List<Movie>) : MovieState()
    data class Error(val message: String) : MovieState()
    object Loading : MovieState()
}

class HomeViewModel(application: Application) : AndroidViewModel(application) {
    private val _movieState = MutableLiveData<MovieState>()
    val movieState: LiveData<MovieState> get() = _movieState

    private val sharedPreferences = application.getSharedPreferences("user_prefs", Context.MODE_PRIVATE)

    fun getMovies() {
        _movieState.postValue(MovieState.Loading)
        val token = sharedPreferences.getString("token", "") ?: ""

        viewModelScope.launch(Dispatchers.IO) {
            try {
                val response: Response<MoviesDTO> = MovieService.api.getAllMovies(token)
                if (response.isSuccessful) {
                    response.body()?.let {
                        _movieState.postValue(MovieState.Success(it.movies))
                    }
                } else {
                    _movieState.postValue(MovieState.Error("Error al obtener películas"))
                }
            } catch (e: Exception) {
                _movieState.postValue(MovieState.Error("Error de conexión: ${e.localizedMessage}"))
            }
        }
    }

    fun createMovie(movieRequest: MovieRequest) {
        _movieState.postValue(MovieState.Loading)
        val token = sharedPreferences.getString("token", "") ?: ""

        viewModelScope.launch(Dispatchers.IO) {
            try {
                val response: Response<MovieDTO> = MovieService.api.createMovie(token, movieRequest)
                if (response.isSuccessful) {
                    response.body()?.let {
                        getMovies()
                        _movieState.postValue(MovieState.Success(emptyList()))
                    }
                } else {
                    _movieState.postValue(MovieState.Error("Error al agregar película"))
                }
            } catch (e: Exception) {
                _movieState.postValue(MovieState.Error("Error de conexión: ${e.localizedMessage}"))
            }
        }
    }

    fun searchMoviesByTitle(title: String) {
        _movieState.postValue(MovieState.Loading)
        val token = sharedPreferences.getString("token", "") ?: ""

        viewModelScope.launch(Dispatchers.IO) {
            try {
                val response: Response<MoviesDTO> = MovieService.api.getMoviesByTitle(token, title)
                if (response.isSuccessful) {
                    response.body()?.let {
                        _movieState.postValue(MovieState.Success(it.movies))
                    }
                } else {
                    _movieState.postValue(MovieState.Error("Error al buscar películas"))
                    getMovies()
                }
            } catch (e: Exception) {
                _movieState.postValue(MovieState.Error("Error de conexión: ${e.localizedMessage}"))
            }
        }
    }
}
