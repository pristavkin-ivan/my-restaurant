package com.vano.myrestaurant.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Drink(
    @PrimaryKey(autoGenerate = true)
    var id: Int,
    var name: String,
    var description: String,
    var volume: Int,
    var resourceID: Int
) {
    override fun toString(): String = name
}
