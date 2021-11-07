package com.vano.myweather.viewmodel

import android.app.Application
import android.widget.Toast
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.vano.myweather.model.database.CityWeatherDatabase
import com.vano.myweather.model.entity.City
import com.vano.myweather.model.entity.CityApi
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
    private val responseRx: MutableLiveData<Response<CityApi>> = MutableLiveData()
    private val savedCity: MutableLiveData<City> = MutableLiveData()
    private val compositeDisposable = CompositeDisposable()
    private val savedCities: MutableLiveData<List<City>> = MutableLiveData()

    init {
        val cityDao = database?.cityDao()
        val cityRxDao = database?.cityRxDao()

        repository = if (cityDao != null && cityRxDao != null)
            CityRepository(cityDao, cityRxDao) else null
    }

    companion object {
        const val ERROR = "Rx exception:"
    }

    fun getAllSavedCities() = repository?.getAllSavedCities()

    fun getAllSavedCitiesRx(): LiveData<List<City>> {
        val disposable =
            repository?.getAllSavedCitiesRx()?.subscribeOn(Schedulers.io())
                ?.observeOn(AndroidSchedulers.mainThread())?.subscribe({
                    savedCities.value = it
                }, {
                    Toast.makeText(
                        getApplication(), ERROR + it.localizedMessage, Toast.LENGTH_LONG
                    ).show()
                })
        disposable?.let {
            compositeDisposable.add(it)
        }
        return savedCities
    }

    fun getSavedCity(name: String) = repository?.getSavedCity(name)

    fun getCity(city: String): LiveData<Response<City>> {
        viewModelScope.launch(Dispatchers.IO) {
            response.postValue(repository?.getCity(city))
        }
        return response
    }

    fun getCityRx(city: String): LiveData<Response<CityApi>> {
        val disposable = repository?.getCityRx(city)?.subscribeOn(Schedulers.io())
            ?.observeOn(AndroidSchedulers.mainThread())?.subscribe({
                responseRx.value = it
            }, {
                Toast.makeText(
                    getApplication(), ERROR + it.localizedMessage, Toast.LENGTH_LONG
                ).show()
            })
        disposable?.let { compositeDisposable.add(it) }
        return responseRx
    }

    fun saveCity(city: City) {
        viewModelScope.launch(Dispatchers.IO) {
            repository?.save(city)
        }
    }

    fun saveCityRx(cityRx: City) {
        viewModelScope.launch(Dispatchers.IO) {
            repository?.saveRx(cityRx)
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

    fun convertCityApiToCity(cityApi: CityApi) =
        City(
            cityApi.name,
            cityApi.main.temp,
            cityApi.weather[0].description,
            cityApi.main.humidity,
            cityApi.main.feels_like
        )

}