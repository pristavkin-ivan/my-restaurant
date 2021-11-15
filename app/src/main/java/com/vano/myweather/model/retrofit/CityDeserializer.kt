package com.vano.myweather.model.retrofit

import com.google.gson.JsonDeserializationContext

import com.google.gson.JsonElement

import com.google.gson.JsonDeserializer
import com.vano.myweather.model.entity.City
import java.lang.reflect.Type


class CityDeserializer : JsonDeserializer<City?> {

    companion object {
        const val WEATHER_KEY = "weather"
        const val DESCRIPTION = "description"
        const val MAIN = "main"
        const val TEMPERATURE = "temp"
        const val NAME = "name"
        const val FEELS_LIKE = "feels_like"
        const val HUMIDITY = "humidity"
    }

    override fun deserialize(
        json: JsonElement,
        typeOfT: Type?,
        context: JsonDeserializationContext?
    ): City {
        val jsonObject = json.asJsonObject

        val name = jsonObject.getAsJsonPrimitive(NAME).asString
        val description = jsonObject
            .getAsJsonArray(WEATHER_KEY).get(0).asJsonObject.getAsJsonPrimitive(DESCRIPTION).asString
        val temp = jsonObject.getAsJsonObject(MAIN).getAsJsonPrimitive(TEMPERATURE).asDouble
        val humidity = jsonObject.getAsJsonObject(MAIN).getAsJsonPrimitive(HUMIDITY)
            .asInt
        val feelsLike = jsonObject.getAsJsonObject(MAIN)
            .getAsJsonPrimitive(FEELS_LIKE).asDouble

        return City(name, temp, description, humidity, feelsLike)
    }

}