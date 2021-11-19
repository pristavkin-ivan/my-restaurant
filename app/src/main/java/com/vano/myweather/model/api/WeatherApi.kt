package com.vano.myweather.model.api

import com.vano.myweather.model.entity.City
import com.vano.myweather.model.entity.CityApi
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import io.reactivex.Single
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query

const val BASE_URL = "https://api.openweathermap.org/"
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

@Module
@InstallIn(ViewModelComponent::class)
object ApiModule {

    @Provides
    fun getWeatherApi(): WeatherApi =
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .build()
            .create(WeatherApi::class.java)
}
