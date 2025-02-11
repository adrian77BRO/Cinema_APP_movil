package com.example.apptransaccional.register.data.repository

import com.example.apptransaccional.core.network.RetrofitHelper
import com.example.apptransaccional.register.data.models.RegisterDTO
import com.example.apptransaccional.register.data.models.RegisterRequest
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.json.JSONObject
import retrofit2.Response

class RegisterRepository {
    suspend fun register(username: String, email: String, password: String): Result<RegisterDTO> {
        return withContext(Dispatchers.IO) {
            try {
                val response: Response<RegisterDTO> = RetrofitHelper.registerService.register(RegisterRequest(username, email, password))
                if (response.isSuccessful) {
                    response.body()?.let {
                        if (it.status == "success") {
                            return@withContext Result.success(it)
                        }
                        return@withContext Result.failure(Exception(it.message))
                    }
                    return@withContext Result.failure(Exception("Respuesta vacía"))
                } else {
                    val errorMessage = response.errorBody()?.string()?.let {
                        try {
                            JSONObject(it).getString("message")
                        } catch (e: Exception) {
                            "Error desconocido"
                        }
                    } ?: "Error de servidor"
                    return@withContext Result.failure(Exception(errorMessage))
                }
            } catch (e: Exception) {
                return@withContext Result.failure(Exception("Error de conexión: ${e.localizedMessage}"))
            }
        }
    }
}