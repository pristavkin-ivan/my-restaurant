package com.vano.myweather.model.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.vano.myweather.model.dao.CityDao
import com.vano.myweather.model.dao.CityRxDao
import com.vano.myweather.model.entity.City

@Database(entities = [City::class], version = 4)
abstract class CityWeatherDatabase : RoomDatabase() {

    abstract fun cityDao(): CityDao

    abstract fun cityRxDao(): CityRxDao

    companion object {
        @Volatile
        private var instance: CityWeatherDatabase? = null

        private const val DB_NAME = "city_weather_database"

        fun getCityWeatherDatabase(context: Context): CityWeatherDatabase {
            var tempInstance = instance

            if (tempInstance == null) {
                synchronized(this) {
                    tempInstance = Room.databaseBuilder(
                        context.applicationContext,
                        CityWeatherDatabase::class.java,
                        DB_NAME
                    ).build()
                    instance = tempInstance
                }
            }

            return tempInstance!!
        }
    }
}