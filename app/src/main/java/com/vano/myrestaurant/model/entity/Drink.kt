package com.vano.myrestaurant.model.entity

data class Drink(
    var id: Int,
    var name: String,
    var description: String,
    var volume: Int,
    var resourceID: Int
) {
    override fun toString(): String = name
}
