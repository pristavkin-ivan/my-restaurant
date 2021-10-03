package com.vano.myrestaurant.model.dao;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.vano.myrestaurant.model.entity.Drink;

import java.util.ArrayList;
import java.util.List;

public class DrinkDao implements Dao<Drink> {

    private final SQLiteOpenHelper dbHelper;
    private static final String[] COLUMNS = {"_id", "name", "description", "volume", "resource"};
    private static final String DRINK_TABLE_NAME = "drink";
    private static final String ID_SELECTION = "_id = ?";
    private static final String NAME_SELECTION = "name = ?";

    public DrinkDao(SQLiteOpenHelper dbHelper) {
        this.dbHelper = dbHelper;
    }

    @Override
    public List<Drink> findAll() {
        final List<Drink> drinks = new ArrayList<>();

        try (final SQLiteDatabase db = dbHelper.getReadableDatabase();
             final Cursor cursor = db.query(DRINK_TABLE_NAME, COLUMNS
                     , null, null, null, null, null)) {

            if(cursor.moveToFirst()) {
                do {
                    drinks.add(new Drink(cursor.getInt(0), cursor.getString(1), cursor.getString(2), cursor.getInt(3)
                            , cursor.getInt(4)));
                } while (cursor.moveToNext());
            }
        } catch (SQLiteException e) {
            handleSQLiteException(e);
        }

        return drinks;
    }

    @Override
    public Drink findById(int id) {
        Drink drink = null;
        try (final SQLiteDatabase db = dbHelper.getReadableDatabase();
             final Cursor cursor = db.query(DRINK_TABLE_NAME, COLUMNS
                     , ID_SELECTION
                     , new String[]{String.valueOf(id + 1)}
                     , null, null, null)) {

            if (cursor.moveToFirst()) {
                drink = new Drink(id, cursor.getString(1), cursor.getString(2), cursor.getInt(3)
                        , cursor.getInt(4));
            }
        } catch (SQLiteException e) {
            handleSQLiteException(e);
        }

        return drink;
    }

    @Override
    public Drink findByName(String name) {
        Drink drink = null;
        try (final SQLiteDatabase db = dbHelper.getReadableDatabase();
             final Cursor cursor = db.query(DRINK_TABLE_NAME, COLUMNS
                     , NAME_SELECTION
                     , new String[]{name}
                     , null, null, null)) {

            if (cursor.moveToFirst()) {
                drink = new Drink(cursor.getInt(0), cursor.getString(1), cursor.getString(2), cursor.getInt(3)
                        , cursor.getInt(4));
            }
        } catch (SQLiteException e) {
            handleSQLiteException(e);
        }

        return drink;
    }

    private void handleSQLiteException(SQLiteException e) {
        Log.e(SQLiteException.class.toString(), e.getLocalizedMessage());
        // todo пробросить exception в активности поймать и вывести Toast
        //Toast.makeText(this, "Some db error!", Toast.LENGTH_LONG).show();
    }


}
