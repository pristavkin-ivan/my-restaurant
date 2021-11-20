package com.vano.myweather.model.api

import com.vano.myweather.model.entity.City
import com.vano.myweather.model.entity.CityApi
import io.reactivex.Single
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

const val API_ID = "8906a0ffe2671dec4d59f899db39ad3b"

interface WeatherApi {

    @GET("data/2.5/weather")
    suspend fun getWeather(
        @Query("q") city: String,
        @Query("units") units: String = "metric",
        @Query("appid") appid: String = API_ID
    ): Response<City>

    @GET("data/2.5/weather")
    fun getWeatherRx(
        @Query("q") city: String,
        @Query("units") units: String = "metric",
        @Query("appid") appid: String = API_ID
    ): Single<Response<CityApi>>
}
