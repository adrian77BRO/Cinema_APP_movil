package com.example.apptransaccional.core.network

import com.example.apptransaccional.home.data.datasource.MovieService
import com.example.apptransaccional.login.data.datasource.LoginService
import com.example.apptransaccional.register.data.datasource.RegisterService
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

object RetrofitHelper {
    private const val BASE_URL = "http://192.168.1.67:8080/"

    private val client = OkHttpClient.Builder()
        .connectTimeout(5, TimeUnit.SECONDS)
        .readTimeout(5, TimeUnit.SECONDS)
        .writeTimeout(5, TimeUnit.SECONDS)
        .build()

    val instance: Retrofit by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(client)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    val movieService: MovieService by lazy {
        instance.create(MovieService::class.java)
    }

    val loginService: LoginService by lazy {
        instance.create(LoginService::class.java)
    }

    val registerService: RegisterService by lazy {
        instance.create(RegisterService::class.java)
    }
}