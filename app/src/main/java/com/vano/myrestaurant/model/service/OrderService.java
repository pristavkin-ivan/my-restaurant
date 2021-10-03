package com.vano.myrestaurant.model.service;

import android.content.Context;
import android.database.sqlite.SQLiteOpenHelper;

import com.vano.myrestaurant.model.RestaurantDatabaseHelper;
import com.vano.myrestaurant.model.dao.Dao;
import com.vano.myrestaurant.model.dao.DrinkDao;
import com.vano.myrestaurant.model.dao.FoodDao;
import com.vano.myrestaurant.model.dao.OrderDao;
import com.vano.myrestaurant.model.entity.Drink;
import com.vano.myrestaurant.model.entity.Food;
import com.vano.myrestaurant.model.entity.Order;

import java.util.List;

public class OrderService {

    private final Dao<Food> foodDao;

    private final Dao<Drink> drinkDao;

    private final Dao<Order> orderDao;

    public OrderService(Context context) {
        SQLiteOpenHelper dbHelper = new RestaurantDatabaseHelper(context);
        foodDao = new FoodDao(dbHelper);
        drinkDao = new DrinkDao(dbHelper);
        orderDao = new OrderDao(dbHelper);
    }

    public int createOrder(String foodName, String drinkName) {
        final Food food = foodDao.findByName(foodName);
        final Drink drink = drinkDao.findByName(drinkName);

        return orderDao.create(new Order(0, food, drink));
    }

    public List<Order> readAllOrders() {
        return orderDao.findAll();
    }

    public void deleteOrder(int orderId) {
        orderDao.delete(orderId);
    }
}
