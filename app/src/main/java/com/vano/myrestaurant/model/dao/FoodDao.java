package com.vano.myrestaurant.model.dao;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.vano.myrestaurant.model.entity.Food;

import java.util.ArrayList;
import java.util.List;

public class FoodDao implements Dao<Food> {

    private final SQLiteOpenHelper dbHelper;
    private static final String[] COLUMNS = {"_id", "name", "description", "weight", "resource", "favorite"};
    private static final String FOOD_TABLE_NAME = "food";
    private static final String FAVORITE_SELECTION = "favorite = ?";
    private static final String ID_SELECTION = "_id = ?";
    private static final String NAME_SELECTION = "name = ?";

    public FoodDao(SQLiteOpenHelper dbHelper) {
        this.dbHelper = dbHelper;
    }

    @Override
    public List<Food> findAll() {
        final ArrayList<Food> food = new ArrayList<>();

        try (final SQLiteDatabase db = dbHelper.getReadableDatabase();
             final Cursor cursor = db.query(FOOD_TABLE_NAME, COLUMNS
                     , null, null, null, null, null)) {

            while (cursor.moveToNext()) {
                food.add(new Food(cursor.getInt(0), cursor.getString(1), cursor.getString(2)
                        , cursor.getInt(3), cursor.getInt(4)));
            }
        } catch (SQLiteException e) {
            handleSQLiteException(e);
        }

        return food;
    }

    @Override
    public List<Food> findAllFavorite() {
        final ArrayList<Food> food = new ArrayList<>();

        try (final SQLiteDatabase db = dbHelper.getReadableDatabase();
             final Cursor cursor = db.query(FOOD_TABLE_NAME, COLUMNS
                     , FAVORITE_SELECTION, new String[]{"1"}, null, null, null)) {

            if (cursor.moveToFirst()) {
                do {
                    food.add(new Food(cursor.getInt(0), cursor.getString(1), cursor.getString(2)
                            , cursor.getInt(3), cursor.getInt(4), cursor.getInt(5) == 1));
                } while (cursor.moveToNext());
            }
        } catch (SQLiteException e) {
            handleSQLiteException(e);
        }

        return food;
    }

    @Override
    public void updateFavoriteColumn(int id, int favorite) {
        try (final SQLiteDatabase db = dbHelper.getWritableDatabase()) {
            ContentValues contentValues = new ContentValues();

            contentValues.put("favorite", favorite);
            db.update(FOOD_TABLE_NAME, contentValues, ID_SELECTION
                    , new String[]{String.valueOf(id + 1)});
        } catch (SQLiteException e) {
            handleSQLiteException(e);
        }
    }

    @Override
    public Food findById(int id) {
        Food food = null;

        try (final SQLiteDatabase db = dbHelper.getReadableDatabase();
             final Cursor cursor = db.query(FOOD_TABLE_NAME
                     , COLUMNS
                     , ID_SELECTION
                     , new String[]{String.valueOf(id + 1)}
                     , null, null, null)) {

            if (cursor.moveToFirst()) {
                food = new Food(id, cursor.getString(1), cursor.getString(2), cursor.getInt(3)
                        , cursor.getInt(4), cursor.getInt(5) == 1);
            }
        } catch (SQLiteException e) {
            handleSQLiteException(e);
        }

        return food;
    }

    @Override
    public Food findByName(String name) {
        Food food = null;
        try (final SQLiteDatabase db = dbHelper.getReadableDatabase();
             final Cursor cursor = db.query(FOOD_TABLE_NAME, COLUMNS
                     , NAME_SELECTION
                     , new String[]{name}
                     , null, null, null)) {

            if (cursor.moveToFirst()) {
                food = new Food(cursor.getInt(0), cursor.getString(1), cursor.getString(2), cursor.getInt(3)
                        , cursor.getInt(4), cursor.getInt(5) == 1);
            }
        } catch (SQLiteException e) {
            handleSQLiteException(e);
        }

        return food;
    }

    private void handleSQLiteException(SQLiteException e) {
        Log.e(SQLiteException.class.toString(), e.getLocalizedMessage());
        // todo пробросить exception в активности поймать и вывести Toast
        //Toast.makeText(this, "Some db error!", Toast.LENGTH_LONG).show();
    }

}
