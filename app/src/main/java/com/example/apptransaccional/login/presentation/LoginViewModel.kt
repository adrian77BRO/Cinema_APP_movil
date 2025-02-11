package com.example.apptransaccional.login.presentation

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.apptransaccional.login.data.models.LoginState
import com.example.apptransaccional.login.data.repository.LoginRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class LoginViewModel(application: Application) : AndroidViewModel(application) {
    private val loginRepository = LoginRepository(application)

    private val _loginState = MutableLiveData<LoginState?>()
    val loginState: LiveData<LoginState?> get() = _loginState

    fun login(email: String, password: String) {
        CoroutineScope(Dispatchers.Main).launch {
            val result = loginRepository.login(email, password)
            result.onSuccess { loginDTO ->
                val username = loginDTO.user?.username ?: "Usuario"
                val token = loginDTO.token ?: ""
                _loginState.value = LoginState.Success(username, token)
            }.onFailure { error ->
                _loginState.value = LoginState.Error(error.localizedMessage ?: "Error desconocido")
            }
        }
    }
}