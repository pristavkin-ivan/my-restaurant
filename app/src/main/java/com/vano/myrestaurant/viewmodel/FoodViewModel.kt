package com.vano.myrestaurant.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import com.vano.myrestaurant.model.db.MyRestaurantDatabase
import com.vano.myrestaurant.model.entity.Drink
import com.vano.myrestaurant.model.entity.Food
import com.vano.myrestaurant.model.repository.FoodRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.util.*

class FoodViewModel(application: Application): AndroidViewModel(application) {

    private val repository: FoodRepository

    init {
        val myRestaurantDatabase: MyRestaurantDatabase = MyRestaurantDatabase
            .getRestaurantDatabase(application)
        repository = FoodRepository(myRestaurantDatabase.foodDao())
    }

    fun readAll() = repository.readAll

    fun readAllFavorite() = repository.readAllFavorite

    fun read(id: Int) = repository.read(id)

    fun addToFavorite(id: Int) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.addToFavorite(id)
        }
    }

    fun deleteFromFavorite(id: Int) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.deleteFromFavorite(id)
        }
    }

    fun insertFood() {
        repository.insertFood()
    }

}