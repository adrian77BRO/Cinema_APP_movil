package com.example.apptransaccional.register.presentation

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
