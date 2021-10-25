package com.vano.myrestaurant.repository

import com.vano.myrestaurant.dao.DrinkDao

class DrinkRepository(private val drinkDao: DrinkDao) {

    val readAll = drinkDao.findAll()

    fun read(id: Int) = drinkDao.find(id)

    /*fun insertDrinks() {
        GlobalScope.launch(Dispatchers.IO) {
            drinkDao.add(
                Drink(
                    0,
                    "Coca cola",
                    "The most popular soda drink all over the world!",
                    500,
                    R.drawable.cola
                )
            )
            drinkDao.add(
                Drink(
                    0, "Black tea", "Hot delicious Indian black tea!", 300, R.drawable.black_tea
                )
            )
            drinkDao.add(
                Drink(
                    0, "Green tea", "Hot delicious Indian green tea!", 300, R.drawable.green_tea
                )
            )
        }
    }*/

}