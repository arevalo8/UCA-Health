package com.ucahealth.app.data.remote

import com.ucahealth.app.ApiConfig
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitClient {

    private val logging = HttpLoggingInterceptor().apply {
        level = HttpLoggingInterceptor.Level.BODY
    }

    private val httpClient = OkHttpClient.Builder()
        .addInterceptor(logging)
        .build()

    val sportsApi: SportsApi by lazy {
        Retrofit.Builder()
            .baseUrl(ApiConfig.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .client(httpClient)
            .build()
            .create(SportsApi::class.java)
    }
}
