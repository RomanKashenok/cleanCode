package com.kashanok.data.net

import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

internal fun provideApi() : ApiRest {

    val retrofit  = Retrofit.Builder()
        .baseUrl("http://kiparo.ru/t/fake-api/car-service.php/")
        .addConverterFactory(GsonConverterFactory.create())
        .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
        .client(VehicleHttpClientBuilder.getHttpClient())
        .build()

    return retrofit.create(ApiRest::class.java)
}