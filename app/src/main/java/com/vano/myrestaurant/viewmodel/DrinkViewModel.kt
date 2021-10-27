package com.vano.myrestaurant.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import com.vano.myrestaurant.model.db.MyRestaurantDatabase
import com.vano.myrestaurant.model.entity.Drink
import com.vano.myrestaurant.model.repository.DrinkRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.util.*

class DrinkViewModel(application: Application): AndroidViewModel(application) {

    private val repository: DrinkRepository

    init {
        val myRestaurantDatabase: MyRestaurantDatabase = MyRestaurantDatabase
            .getRestaurantDatabase(application)
        repository = DrinkRepository(myRestaurantDatabase.drinkDao())
    }

    fun insertDrink() = repository.insertDrinks()

    fun readAll() = repository.readAll

    fun read(id: Int) = repository.read(id)
}