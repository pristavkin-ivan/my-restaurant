package com.vano.myrestaurant.model.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Order(@PrimaryKey(autoGenerate = true) var id: Int, var foodId: Int, var drinkId: Int)