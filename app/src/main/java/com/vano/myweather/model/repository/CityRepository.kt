package com.vano.myweather.model.repository

import com.vano.myweather.model.api.WeatherApi
import com.vano.myweather.model.dao.CityDao
import com.vano.myweather.model.dao.CityRxDao
import com.vano.myweather.model.entity.City
import com.vano.myweather.model.entity.CityApi
import dagger.hilt.android.scopes.ViewModelScoped
import io.reactivex.Single
import retrofit2.Response
import javax.inject.Inject

@ViewModelScoped
class CityRepository @Inject constructor(
    private val dao: CityDao,
    private val daoRx: CityRxDao,
    private var api: WeatherApi
) {

    suspend fun save(city: City) = dao.save(city)

    suspend fun saveRx(city: City) = daoRx.save(city)

    suspend fun getCity(city: String) = api.getWeather(city)

    fun getCityRx(city: String): Single<Response<CityApi>> {
        Thread.sleep(800)
        return api.getWeatherRx(city)
    }

    fun getAllSavedCities() = dao.getAll()

    fun getAllSavedCitiesRx() = daoRx.getAll()

    fun getSavedCity(name: String) = dao.get(name)

    suspend fun update(city: City?) = city?.let {
        dao.update(city)
        dao.get(city.name)
    }
}