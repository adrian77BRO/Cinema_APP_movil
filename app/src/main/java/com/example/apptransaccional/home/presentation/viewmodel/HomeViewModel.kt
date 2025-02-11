package com.example.apptransaccional.home.presentation.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.apptransaccional.home.data.models.MovieRequest
import com.example.apptransaccional.home.data.models.MovieState
import com.example.apptransaccional.home.domain.MovieRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class HomeViewModel(application: Application) : AndroidViewModel(application) {
    private val repository = MovieRepository(application.applicationContext)
    private val _movieState = MutableLiveData<MovieState>()
    val movieState: LiveData<MovieState> get() = _movieState

    fun getMovies() {
        _movieState.postValue(MovieState.Loading)
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val response = repository.getMovies()
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
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val response = repository.createMovie(movieRequest)
                if (response.isSuccessful) {
                    getMovies()
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
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val response = repository.searchMoviesByTitle(title)
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