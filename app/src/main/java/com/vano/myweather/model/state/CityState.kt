package com.vano.myweather.model.state

import com.vano.myweather.model.entity.City

sealed class CityState {
    object LoadingCityState: CityState()
    object EmptyCityState: CityState()
    class LoadedCityState(val city: City): CityState()
    class ErrorCityState(val errorMessage: String): CityState()
}
