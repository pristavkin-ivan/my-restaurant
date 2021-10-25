package com.vano.myrestaurant.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import com.vano.myrestaurant.db.MyRestaurantDatabase
import com.vano.myrestaurant.entity.Drink
import com.vano.myrestaurant.entity.Food
import com.vano.myrestaurant.repository.FoodRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.util.*

class FoodViewModel(application: Application): AndroidViewModel(application) {

    private val repository: FoodRepository

    val readAll: LiveData<List<Food>>

    val readAllFavorite: LiveData<List<Food>>

    init {
        val myRestaurantDatabase: MyRestaurantDatabase = MyRestaurantDatabase
            .getRestaurantDatabase(application)
        repository = FoodRepository(myRestaurantDatabase.foodDao())
        readAll = repository.readAll
        readAllFavorite = repository.readAllFavorite
    }

    fun read(id: Int) = repository.read(id + 1)

    fun addToFavorite(id: Int) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.addToFavorite(id + 1)
        }
    }

    fun deleteFromFavorite(id: Int) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.deleteFromFavorite(id + 1)
        }
    }

}