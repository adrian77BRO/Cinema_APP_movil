package com.example.apptransaccional.register.presentation

/*import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.apptransaccional.register.data.repository.RegisterRepository
import com.example.apptransaccional.register.domain.RegisterUseCase
import kotlinx.coroutines.launch

class RegisterViewModel : ViewModel() {
    private val repository = RegisterRepository()
    val registerResult = MutableLiveData<String>()

    fun register(username: String, email: String, password: String) {
        viewModelScope.launch {
            val response = repository.register(username, email, password)
            if (response.isSuccessful) {
                registerResult.postValue("Registro exitoso")
            } else {
                registerResult.postValue("Error en registro")
            }
        }
    }
}*/

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.apptransaccional.register.data.datasource.RegisterService
import com.example.apptransaccional.register.data.models.RegisterRequest
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.json.JSONObject

class RegisterViewModel : ViewModel() {
    private val _registerResult = MutableLiveData<String?>()
    val registerResult: LiveData<String?> get() = _registerResult

    fun register(username: String, email: String, password: String) {
        CoroutineScope(Dispatchers.IO).launch {
            try {
                val response = RegisterService.api.register(RegisterRequest(username, email, password))
                if (response.isSuccessful) {
                    val registerResponse = response.body()
                    if (registerResponse?.status == "success") {
                        _registerResult.postValue(registerResponse.message)
                    } else {
                        _registerResult.postValue(registerResponse?.message ?: "Error en el registro")
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

                    _registerResult.postValue(errorMessage)
                }
            } catch (e: Exception) {
                _registerResult.postValue("Error de conexión: ${e.localizedMessage}")
            }
        }
    }
}
