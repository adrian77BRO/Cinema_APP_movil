package com.example.apptransaccional.register.presentation

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.apptransaccional.register.data.datasource.RegisterService
import com.example.apptransaccional.register.data.models.RegisterRequest
import com.example.apptransaccional.register.data.models.RegisterState
import com.example.apptransaccional.register.data.repository.RegisterRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.json.JSONObject

class RegisterViewModel : ViewModel() {
    private val registerRepository = RegisterRepository()

    private val _registerState = MutableLiveData<RegisterState?>()
    val registerState: LiveData<RegisterState?> get() = _registerState

    fun register(username: String, email: String, password: String) {
        CoroutineScope(Dispatchers.Main).launch {
            val result = registerRepository.register(username, email, password)
            result.onSuccess { registerDTO ->
                _registerState.value = RegisterState.Success(registerDTO.message)
            }.onFailure { error ->
                _registerState.value = RegisterState.Error(error.localizedMessage ?: "Error desconocido")
            }
        }
    }

    fun clearState() {
        _registerState.value = null
    }

}