package com.vano.myweather.model.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class City(
    @PrimaryKey
    val name: String,
    val temperature: Double,
    val description: String,
    val humidity: Double,
    val feelsLikeTemperature: Double,
) {
    override fun toString(): String {
        return name
    }
}