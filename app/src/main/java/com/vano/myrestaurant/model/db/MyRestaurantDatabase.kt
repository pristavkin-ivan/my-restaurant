package com.vano.myrestaurant.model.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.vano.myrestaurant.model.dao.DrinkDao
import com.vano.myrestaurant.model.dao.FoodDao
import com.vano.myrestaurant.model.dao.OrderDao
import com.vano.myrestaurant.model.entity.Drink
import com.vano.myrestaurant.model.entity.Food
import com.vano.myrestaurant.model.entity.Order

@Database(entities = [Drink::class, Food::class, Order::class], version = 2)
abstract class MyRestaurantDatabase : RoomDatabase() {

    abstract fun drinkDao(): DrinkDao

    abstract fun foodDao(): FoodDao

    abstract fun orderDao(): OrderDao

    companion object {
        @Volatile
        private var instance: MyRestaurantDatabase? = null

        fun getRestaurantDatabase(context: Context): MyRestaurantDatabase {
            val tempInstance = instance

            if (tempInstance != null) {
                return tempInstance
            }

            synchronized(this) {
                val inst = Room.databaseBuilder(
                    context.applicationContext, MyRestaurantDatabase::class.java, "my_restaurant"
                ).fallbackToDestructiveMigration().build()

                instance = inst
                return inst
            }
        }
    }
}

