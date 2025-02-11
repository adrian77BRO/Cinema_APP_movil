package com.example.apptransaccional.login.data.datasource

import com.example.apptransaccional.login.data.models.LoginDTO
import com.example.apptransaccional.login.data.models.LoginRequest
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST

interface LoginService {
    @POST("users/login")
    suspend fun login(@Body request : LoginRequest) : Response<LoginDTO>
}