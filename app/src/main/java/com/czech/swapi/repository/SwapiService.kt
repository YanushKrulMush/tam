package com.czech.swapi.repository

import com.czech.swapi.repository.model.Person
import com.czech.swapi.repository.model.SwapiResponse
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Path

interface SwapiService {

    @GET("/api/people")
    suspend fun getSwapiResponse(): Response<SwapiResponse>

    @GET("/api/people/{id}")
    suspend fun getPersonById(@Path("id") id: Int): Response<Person>

    companion object {
        private const val STAR_URL = "https://swapi.dev/"

        private val logger =
            HttpLoggingInterceptor().apply {
                this.level = HttpLoggingInterceptor.Level.BODY
            }

        private val okHttp = OkHttpClient.Builder().apply {
            this.addInterceptor(logger)
        }.build()

        private val retrofit: Retrofit by lazy {
            Retrofit.Builder()
                .baseUrl(STAR_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
        }

        val swapiService: SwapiService by lazy {
            retrofit.create(SwapiService::class.java)
        }
    }
}