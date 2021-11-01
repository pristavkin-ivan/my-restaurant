package com.vano.myweather.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.vano.myweather.model.database.CityWeatherDatabase
import com.vano.myweather.model.entity.City
import com.vano.myweather.model.repository.CityRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.Response

class CityViewModel(application: Application) : AndroidViewModel(application) {
    private val database =
        CityWeatherDatabase.getCityWeatherDatabase(application.applicationContext)
    private val repository: CityRepository?
    private val response: MutableLiveData<Response<City>> = MutableLiveData()

    init {
        val cityDao = database?.cityDao()
        repository = cityDao?.let { CityRepository(it) }
    }

    fun getAllSavedCities() = repository?.getAllSavedCities()

    fun getSavedCity(id: Int) = repository?.getSavedCity(id)

    fun getCity(city: String): LiveData<Response<City>> {
        viewModelScope.launch(Dispatchers.IO) {
            response.postValue(repository?.getCity(city))
        }
        return response
    }

    fun saveCity(city: City) {
        viewModelScope.launch(Dispatchers.IO) {
            repository?.save(city)
        }
    }
}