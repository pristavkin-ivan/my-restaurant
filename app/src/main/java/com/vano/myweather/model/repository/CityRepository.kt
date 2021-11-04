package com.vano.myweather.model.repository

import com.vano.myweather.model.dao.CityDao
import com.vano.myweather.model.entity.City
import com.vano.myweather.model.retrofit.RetrofitInstance

class CityRepository(private val dao: CityDao) {

    suspend fun save(city: City) = dao.save(city)

    suspend fun getCity(city: String) = RetrofitInstance.api.getWeather(city)

    fun getAllSavedCities() = dao.getAll()

    fun getSavedCity(name: String) = dao.get(name)

    suspend fun update(city: City?) = city?.let {
        dao.update(city)
        dao.get(city.name)
    }


}