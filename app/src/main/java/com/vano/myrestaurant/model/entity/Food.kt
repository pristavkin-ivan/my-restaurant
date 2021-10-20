package com.vano.myrestaurant.model.entity


data class Food @JvmOverloads constructor(
    var id: Int,
    var name: String,
    var description: String,
    var weight: Int,
    var resourceId: Int,
    var favorite: Boolean = false
) {
    override fun toString() = name
}