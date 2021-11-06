package com.vano.myweather.viewmodel

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.vano.myweather.model.database.CityWeatherDatabase
import com.vano.myweather.model.entity.City
import com.vano.myweather.model.repository.CityRepository
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.Response
import io.reactivex.schedulers.Schedulers

class CityViewModel(application: Application) : AndroidViewModel(application) {
    private val database =
        CityWeatherDatabase.getCityWeatherDatabase(application.applicationContext)
    private val repository: CityRepository?
    private val response: MutableLiveData<Response<City>> = MutableLiveData()
    private val savedCity: MutableLiveData<City> = MutableLiveData()
    private val compositeDisposable = CompositeDisposable()

    init {
        val cityDao = database?.cityDao()
        repository = cityDao?.let { CityRepository(it) }
    }

    fun getAllSavedCities() = repository?.getAllSavedCities()

    fun getSavedCity(name: String) = repository?.getSavedCity(name)

    fun getCity(city: String): LiveData<Response<City>> {
        viewModelScope.launch(Dispatchers.IO) {
            response.postValue(repository?.getCity(city))
        }
        return response
    }

    fun getCityRx(city: String): LiveData<City> {
        compositeDisposable.add(
            repository?.getCityRx(city)?.subscribeOn(Schedulers.io())?.observeOn(AndroidSchedulers.mainThread())?.subscribe({
            if( it.isSuccessful) Log.i("rx", "Response:${it.body()}")
        }
        , {

        })!!
        )
        return MutableLiveData()
    }

    fun saveCity(city: City) {
        viewModelScope.launch(Dispatchers.IO) {
            repository?.save(city)
        }
    }

    fun updateCityInDb(city: City?): LiveData<City> {
        viewModelScope.launch(Dispatchers.IO) {
            savedCity.postValue(repository?.update(city)?.value)
        }
        return savedCity
    }

    override fun onCleared() {
        compositeDisposable.dispose()
        super.onCleared()
    }
}