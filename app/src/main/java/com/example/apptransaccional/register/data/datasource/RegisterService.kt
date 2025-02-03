package com.example.apptransaccional.register.data.datasource

import com.example.apptransaccional.core.network.RetrofitClient
import com.example.apptransaccional.register.data.models.RegisterRequest
import com.example.apptransaccional.register.data.models.RegisterDTO
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST

interface RegisterService {
    @POST("users/register")
    suspend fun register(@Body request : RegisterRequest) : Response<RegisterDTO>

    companion object {
        val api: RegisterService by lazy {
            RetrofitClient.instance.create(RegisterService::class.java)
        }
    }
}