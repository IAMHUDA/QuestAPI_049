package com.example.resapi.containerapp

import com.example.resapi.repository.MahasiswaRepository
import com.example.resapi.repository.NetworkKontakRepository
import com.example.resapi.service.MahasiswaService
import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import retrofit2.Retrofit


interface AppContainer{
    val kontakRepository: MahasiswaRepository
}

class MahasiswaContainer: AppContainer{
    private val baseUrl = "http://10.0.2.2/umyTI/"
    private val json = Json { ignoreUnknownKeys = true }
    private val retrofit: Retrofit = Retrofit.Builder()
        .addConverterFactory(json.asConverterFactory("application/json".toMediaType()))
        .baseUrl(baseUrl).build()

    private val mahasiswaService: MahasiswaService by lazy {
        retrofit.create(mahasiswaService::class.java)
    }

    override val kontakRepository: MahasiswaRepository by lazy {
        NetworkKontakRepository(mahasiswaService)
    }
}