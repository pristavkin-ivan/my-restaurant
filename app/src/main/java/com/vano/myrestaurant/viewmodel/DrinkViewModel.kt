package com.vano.myrestaurant.viewmodel

import android.app.Application
import com.vano.myrestaurant.model.repository.DrinkRepository

class DrinkViewModel(application: Application): BaseViewModel(application) {

    private val repository: DrinkRepository = DrinkRepository(myRestaurantDatabase.drinkDao())

    fun insertDrink() = repository.insertDrinks()

    fun readAll() = repository.readAll

    fun read(id: Int) = repository.read(id)
}