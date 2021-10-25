package com.vano.myrestaurant.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import com.vano.myrestaurant.db.MyRestaurantDatabase
import com.vano.myrestaurant.entity.Drink
import com.vano.myrestaurant.repository.DrinkRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.util.*

class DrinkViewModel(application: Application): AndroidViewModel(application) {

    private val repository: DrinkRepository

    val readAll: LiveData<List<Drink>>

    init {
        val myRestaurantDatabase: MyRestaurantDatabase = MyRestaurantDatabase
            .getRestaurantDatabase(application)
        repository = DrinkRepository(myRestaurantDatabase.drinkDao())
        readAll = repository.readAll
    }

    fun read(id: Int) = repository.read(id + 1)

}