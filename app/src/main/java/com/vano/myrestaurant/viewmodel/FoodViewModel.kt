package com.vano.myrestaurant.viewmodel

import android.app.Application
import androidx.lifecycle.viewModelScope
import com.vano.myrestaurant.model.repository.FoodRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class FoodViewModel(application: Application): BaseViewModel(application) {

    private val repository: FoodRepository = FoodRepository(myRestaurantDatabase.foodDao())

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