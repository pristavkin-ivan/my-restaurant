package com.vano.myweather.model.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

@Entity
data class City(
    @PrimaryKey(autoGenerate = true)
    val id: Int,
    val name: String,
    val temperature: Double,
    val description: String,
    val humidity: Double,
    val feelsLikeTemperature: Double,
)