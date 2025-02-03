package com.example.apptransaccional.login.presentation

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.apptransaccional.login.data.datasource.LoginService
import com.example.apptransaccional.login.data.models.LoginRequest
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.json.JSONObject

/*class LoginViewModel : ViewModel() {
    private val repository = LoginRepository()
    val loginResult = MutableLiveData<String>()

    fun login(username: String, password: String) {
        viewModelScope.launch {
            val response = repository.login(username, password)
            if (response.isSuccessful) {
                loginResult.postValue("Login exitoso")
            } else {
                loginResult.postValue("Error en login")
            }
        }
    }
}*/

class LoginViewModel : ViewModel() {
    private val _loginResult = MutableLiveData<String?>()
    val loginResult: LiveData<String?> get() = _loginResult

    fun login(email: String, password: String) {
        CoroutineScope(Dispatchers.IO).launch {
            try {
                val response = LoginService.api.login(LoginRequest(email, password))
                if (response.isSuccessful) {
                    val loginResponse = response.body()
                    if (loginResponse?.status == "success") {
                        _loginResult.postValue("Bienvenido ${loginResponse.user?.username}")
                    } else {
                        _loginResult.postValue(loginResponse?.message ?: "Error desconocido")
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

                    _loginResult.postValue(errorMessage)
                }
            } catch (e: Exception) {
                _loginResult.postValue("Error de conexión: ${e.localizedMessage}")
            }
        }
    }
}
