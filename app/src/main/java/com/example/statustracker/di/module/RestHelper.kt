package com.example.statustracker.di.module

import com.example.statustracker.network.GoogleSheetService
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.core.component.KoinComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object  RestHelper:KoinComponent {

    private fun loggingInterceptor() =
        HttpLoggingInterceptor().apply {
            level = HttpLoggingInterceptor.Level.BODY
        }

    private fun httpClient() =
        OkHttpClient.Builder().apply {
            addInterceptor(loggingInterceptor())
        }.build()

    private val retrofit = Retrofit.Builder().apply {
        baseUrl("https://script.google.com")
        addConverterFactory(GsonConverterFactory.create())
        client(httpClient())
    }.build()

    val client:GoogleSheetService by lazy{
        retrofit.create(GoogleSheetService::class.java)
    }
}