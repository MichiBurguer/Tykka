package com.example.tykka.data.network

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Path

interface EmisorApiService {

    // Simula la consulta de emisor/empresa por ID o RUC
    @GET("users/{id}")
    suspend fun getEmisorPorId(@Path("id") id: Int): EmisorDto

    // También podemos consultar una lista completa de proveedores verificados
    @GET("users")
    suspend fun getEmisoresList(): List<EmisorDto>

    companion object {
        // Usamos JSONPlaceholder como API de pruebas
        private const val BASE_URL = "https://jsonplaceholder.typicode.com/"

        fun create(): EmisorApiService {
            return Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
                .create(EmisorApiService::class.java)
        }
    }
}