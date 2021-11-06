package com.vano.myweather.model.entity

data class CityRx (
    val coord: Coord? = null,
    val weather: List<Any>,
    val base: String,
    val main: Main?,
    val visibility: Int,
    val wind: Wind?,
    val clouds: Clouds?,
    val dt: Int,
    val sys: Sys?,
    val timezone: Int,
    val id: Int,
    val name: String?,
    val cod: Int
)

data class Sys (
    val type: Int,
    val id: Int,
    val message: Double,
    val country: String?,
    val sunrise: Int,
    val sunset: Int
)

data class Clouds (
    val all: Double
)

data class Wind (
    val speed: Double,
    val deg: Double
)

data class Main (
    val temp: Double,
    val feels_like: Double,
    val temp_min: Double,
    val temp_max: Double,
    val pressure: Double,
    val humidity: Double
)

data class Coord (
    val lon: Double,
    val lat: Double
)