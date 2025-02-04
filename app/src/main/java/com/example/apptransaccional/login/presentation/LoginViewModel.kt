package com.example.apptransaccional.login.presentation

import android.app.Application
import android.content.Context
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.apptransaccional.login.data.datasource.LoginService
import com.example.apptransaccional.login.data.models.LoginRequest
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.json.JSONObject

sealed class LoginState {
    data class Success(val username: String, val token: String) : LoginState()
    data class Error(val message: String) : LoginState()
}

class LoginViewModel(application: Application) : AndroidViewModel(application) {
    private val _loginState = MutableLiveData<LoginState?>()
    val loginState: LiveData<LoginState?> get() = _loginState

    private val sharedPreferences = application.getSharedPreferences("user_prefs", Context.MODE_PRIVATE)

    fun login(email: String, password: String) {
        CoroutineScope(Dispatchers.IO).launch {
            try {
                val response = LoginService.api.login(LoginRequest(email, password))
                if (response.isSuccessful) {
                    val loginResponse = response.body()
                    if (loginResponse?.status == "success") {
                        val username = loginResponse.user?.username ?: "Usuario"
                        val token = loginResponse.token ?: ""

                        sharedPreferences.edit().apply {
                            putString("username", username)
                            putString("token", token)
                            apply()
                        }

                        _loginState.postValue(LoginState.Success(username, token))
                    } else {
                        _loginState.postValue(LoginState.Error(loginResponse?.message ?: "Error desconocido"))
                    }
                } else {
                    val errorBody = response.errorBody()?.string()
                    val errorMessage = errorBody?.let {
                        try {
                            JSONObject(it).getString("message")
                        } catch (e: Exception) {
                            "Error desconocido"
                        }
                    } ?: "Error de servidor"

                    _loginState.postValue(LoginState.Error(errorMessage))
                }
            } catch (e: Exception) {
                _loginState.postValue(LoginState.Error("Error de conexión: ${e.localizedMessage}"))
            }
        }
    }
}
