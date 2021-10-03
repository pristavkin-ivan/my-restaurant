package com.vano.myrestaurant.model.dao;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import androidx.annotation.NonNull;

import com.vano.myrestaurant.model.entity.Drink;
import com.vano.myrestaurant.model.entity.Food;
import com.vano.myrestaurant.model.entity.Order;

import java.util.ArrayList;
import java.util.List;

public class OrderDao implements Dao<Order> {

    private final SQLiteOpenHelper dbHelper;
    private static final String[] COLUMNS = {"_id", "food_id", "drink_id"};
    private static final String ORDERS_TABLE_NAME = "orders";
    private static final String ID_SELECTION = "_id = ?";
    private static final String SELECT_ALL_ORDERS = "select * from orders " +
            "join food on food_id = food._id " +
            "join drink on drink_id = drink._id";


    public OrderDao(SQLiteOpenHelper dbHelper) {
        this.dbHelper = dbHelper;
    }

    @Override
    public List<Order> findAll() {
        final List<Order> orders = new ArrayList<>();

        try (final SQLiteDatabase db = dbHelper.getReadableDatabase();
             final Cursor cursor = db.rawQuery(SELECT_ALL_ORDERS, null)) {

            while (cursor.moveToNext()) {
                orders.add(getOrder(cursor));
            }
        } catch (SQLiteException e) {
            handleSQLiteException(e);
        }
        System.out.println(orders);
        return orders;
    }

    private Order getOrder(Cursor cursor) {
        Food food = new Food(cursor.getInt(3), cursor.getString(4), cursor.getString(5), cursor.getInt(6)
                , cursor.getInt(7), cursor.getInt(8) == 1);
        Drink drink = new Drink(cursor.getInt(9), cursor.getString(10), cursor.getString(11), cursor.getInt(12)
                , cursor.getInt(13));
        return new Order(cursor.getInt(0), food, drink);
    }

    @Override
    public Order findById(int id) {
        return null;
    }

    @Override
    public int create(Order order) {
        try (final SQLiteDatabase db = dbHelper.getWritableDatabase()) {
            return (int) db.insert(ORDERS_TABLE_NAME, null, getContentValues(order));
        } catch (SQLiteException e) {
            handleSQLiteException(e);
        }
        return -1;
    }

    @Override
    public void delete(int id) {
        try (final SQLiteDatabase db = dbHelper.getWritableDatabase()) {
            db.delete(ORDERS_TABLE_NAME, ID_SELECTION, new String[]{"4"});
        } catch (SQLiteException e) {
            handleSQLiteException(e);
        }
    }

    @NonNull
    private ContentValues getContentValues(Order order) {
        final ContentValues contentValues = new ContentValues();

        contentValues.put("food_id", order.getFood().getId());
        contentValues.put("drink_id", order.getDrink().getId());
        return contentValues;
    }

    private void handleSQLiteException(SQLiteException e) {
        Log.e(SQLiteException.class.toString(), e.getLocalizedMessage());
        // todo пробросить exception в активности поймать и вывести Toast
        //Toast.makeText(this, "Some db error!", Toast.LENGTH_LONG).show();
    }

}
