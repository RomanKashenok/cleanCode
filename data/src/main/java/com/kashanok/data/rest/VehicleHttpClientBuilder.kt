package com.kashanok.data.rest

import com.kashanok.data.BuildConfig
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import java.util.concurrent.TimeUnit

object VehicleHttpClientBuilder {

    fun getHttpClient(): OkHttpClient {
        val clientBuilder = OkHttpClient.Builder()
        if (BuildConfig.DEBUG) {
            clientBuilder.addInterceptor(HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY))
        }
        clientBuilder.connectTimeout(5, TimeUnit.SECONDS)
        return clientBuilder.build()
    }
}