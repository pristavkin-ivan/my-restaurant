package com.vano.myweather.model.retrofit

import com.google.gson.GsonBuilder
import com.vano.myweather.model.api.WeatherApi
import com.vano.myweather.model.entity.City
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitInstance {

    private const val BASE_URL = "https://api.openweathermap.org/"

    private val retrofit by lazy {
        val gson = GsonBuilder()
            .setPrettyPrinting()
            .registerTypeAdapter(City::class.java, CityDeserializer())
            .create()
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create(gson))
            .build()
    }

    val api: WeatherApi by lazy {
        retrofit.create(WeatherApi::class.java)
    }
}