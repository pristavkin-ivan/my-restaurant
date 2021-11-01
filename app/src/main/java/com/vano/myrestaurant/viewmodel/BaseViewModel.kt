package com.vano.myrestaurant.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import com.vano.myrestaurant.model.db.MyRestaurantDatabase

open class BaseViewModel(application: Application): AndroidViewModel(application) {
    val myRestaurantDatabase: MyRestaurantDatabase = MyRestaurantDatabase
        .getRestaurantDatabase(application)
}