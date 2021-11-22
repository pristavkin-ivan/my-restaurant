package com.vano.myweather.viewmodel

import android.app.Application
import android.widget.Toast
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.vano.myweather.model.entity.City
import com.vano.myweather.model.entity.CityApi
import com.vano.myweather.model.repository.CityRepository
import com.vano.myweather.model.state.CityState
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.lifecycle.HiltViewModel
import io.reactivex.Observable
import io.reactivex.Scheduler
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import io.reactivex.subjects.BehaviorSubject
import io.reactivex.subjects.Subject
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.Response
import javax.inject.Inject

@HiltViewModel
class CityViewModel @Inject constructor(
    private val repository: CityRepository,
    private val dispatchers: DispatchersWrapper,
    private val schedulers: SchedulersWrapper,
    application: Application
) : AndroidViewModel(application) {

    private val response: MutableLiveData<Response<City>> = MutableLiveData()
    private val savedCity: MutableLiveData<City> = MutableLiveData()
    private val compositeDisposable = CompositeDisposable()
    private val savedCities: MutableLiveData<List<City>> = MutableLiveData()
    private var disposable: Disposable? = null
    private var disposable1: Disposable? = null
    private var disposable2: Disposable? = null
    private var disposable3: Disposable? = null
    private var subject: Subject<CityState>? = null
    val stateData = MutableLiveData<CityState>()

    companion object {
        const val ERROR = "Rx exception:"
    }

    fun getAllSavedCities() = repository.getAllSavedCities()

    fun getAllSavedCitiesRx(): LiveData<List<City>> {
        disposable3 =
            repository.getAllSavedCitiesRx().subscribeOn(schedulers.io())
                ?.observeOn(schedulers.ui())?.subscribe({
                    savedCities.value = it
                }, {
                    Toast.makeText(
                        getApplication(), ERROR + it.localizedMessage, Toast.LENGTH_LONG
                    ).show()
                })
        addDisposableToCompositeDisposable(disposable3)
        return savedCities
    }

    fun getSavedCity(name: String) = repository.getSavedCity(name)

    fun getCity(city: String): LiveData<Response<City>> {
        viewModelScope.launch(dispatchers.io) {
            response.postValue(repository.getCity(city))
        }
        return response
    }

    fun getCityRx1(city1: String, city2: String): Subject<CityState>? {
        stateData.value = CityState.LoadingCityState
        val observable1 = getObservableCity(city1).toObservable()
        val observable2 = getObservableCity(city2).toObservable()

        subject = BehaviorSubject.create()

        disposable1 = getDisposable(observable1, subject)
        disposable2 = getDisposable(observable2, subject)

        addDisposableToCompositeDisposable(disposable1, disposable2)

        return subject
    }

    private fun addDisposableToCompositeDisposable(vararg disposable: Disposable?) =
        compositeDisposable.addAll(*disposable)

    private fun getDisposable(
        observable: Observable<City>?,
        subject: Subject<CityState>?
    ) =
        observable?.subscribe({
            if (it == null) stateData.value = CityState.EmptyCityState else {
                subject?.onNext(CityState.LoadedCityState(it))
            }
        }, {
            subject?.onNext(CityState.ErrorCityState(it.toString()))
        })

    fun getCityRx(cityName: String) {
        stateData.value = CityState.LoadingCityState
        disposable = getObservableCity(cityName)
            .subscribe({
                stateData.value = CityState.LoadedCityState(it)
            }, {
                stateData.value = CityState.ErrorCityState(it.toString())
            })
        addDisposableToCompositeDisposable(disposable)
    }

    fun getObservableCity(city: String) =
        repository.getCityRx(city)
            .subscribeOn(schedulers.io())
            .map {
                it.body()?.let { it1 -> convertCityApiToCity(it1) }
            }
            .observeOn(schedulers.ui())

    fun saveCity(city: City) {
        viewModelScope.launch(dispatchers.io) {
            repository.save(city)
        }
    }

    fun updateCityInDb(city: City?): LiveData<City> {
        viewModelScope.launch(dispatchers.io) {
            savedCity.postValue(repository.update(city)?.value)
        }
        return savedCity
    }

    override fun onCleared() {
        subject?.onNext(CityState.EmptyCityState)
        compositeDisposable.clear()
        super.onCleared()
    }

    private fun convertCityApiToCity(cityApi: CityApi) =
        City(
            cityApi.name,
            cityApi.main.temp,
            cityApi.weather[0].description,
            cityApi.main.humidity,
            cityApi.main.feels_like
        )

}

interface DispatchersWrapper {
    val main: CoroutineDispatcher
    val io: CoroutineDispatcher
    val default: CoroutineDispatcher
}

class CoroutineDispatchersWrapper @Inject constructor() : DispatchersWrapper {
    override val main = Dispatchers.Main
    override val io = Dispatchers.IO
    override val default = Dispatchers.Default
}

interface SchedulersWrapper {
    fun io(): Scheduler
    fun ui(): Scheduler
}

class SchedulersWrapperImpl @Inject constructor() : SchedulersWrapper {
    override fun io() = Schedulers.io()
    override fun ui(): Scheduler = AndroidSchedulers.mainThread()
}

@Module
@InstallIn(ViewModelComponent::class)
abstract class CityViewModelModule {

    @Binds
    abstract fun getDispatchersWrapper(wrapper: CoroutineDispatchersWrapper): DispatchersWrapper

    @Binds
    abstract fun getSchedulersWrapper(wrapper: SchedulersWrapperImpl): SchedulersWrapper
}