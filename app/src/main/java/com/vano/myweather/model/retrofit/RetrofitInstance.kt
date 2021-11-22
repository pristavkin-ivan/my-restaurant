package com.vano.myweather.model.retrofit

import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.vano.myweather.model.api.WeatherApi
import com.vano.myweather.model.entity.City
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

@Module
@InstallIn(SingletonComponent::class)
object RetrofitModule {

    private const val BASE_URL = "https://api.openweathermap.org/"

    @Provides
    fun getGsonConverter(cityDeserializer: CityDeserializer): Gson =
        GsonBuilder()
            .setPrettyPrinting()
            .registerTypeAdapter(City::class.java, cityDeserializer)
            .create()

    @Provides
    fun getRetrofitInstance(): Retrofit =
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .build()

    @Provides
    fun getWeatherApi(retrofit: Retrofit): WeatherApi = retrofit.create(WeatherApi::class.java)
}
