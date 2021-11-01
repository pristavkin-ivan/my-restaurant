package com.vano.myweather.model.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.vano.myweather.model.dao.CityDao
import com.vano.myweather.model.entity.City

@Database(entities = [City::class], version = 1)
abstract class CityWeatherDatabase : RoomDatabase() {

    abstract fun cityDao(): CityDao

    companion object {
        @Volatile
        private var instance: CityWeatherDatabase? = null

        fun getCityWeatherDatabase(context: Context): CityWeatherDatabase? {
            var tempInstance = instance

            if (tempInstance == null) {
                synchronized(this) {
                    tempInstance = Room.databaseBuilder(
                        context.applicationContext,
                        CityWeatherDatabase::class.java,
                        "city_weather_database"
                    ).build()
                    instance = tempInstance
                }
            }

            return tempInstance
        }
    }
}