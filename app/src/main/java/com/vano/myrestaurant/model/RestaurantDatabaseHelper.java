package com.vano.myrestaurant.model;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import com.vano.myrestaurant.R;

public class RestaurantDatabaseHelper extends SQLiteOpenHelper {

    private final static String DB_NAME = "restaurant.db";

    private final static int DB_VERSION = 3;

    private final static String CREATE_FOOD_TABLE = "create table food(" +
            "_id integer primary key autoincrement" +
            ", name text" +
            ", description text" +
            ", weight integer" +
            ", resource integer)";

    private final static String CREATE_DRINK_TABLE = "create table drink(" +
            "_id integer primary key autoincrement" +
            ", name text" +
            ", description text" +
            ", volume integer" +
            ", resource integer)";

    private final static String CREATE_ORDERS_TABLE = "create table orders(" +
            "_id integer primary key autoincrement" +
            ", food_id integer" +
            ", drink_id integer" +
            ", foreign key (food_id) references food(_id) on delete no action on update no action" +
            ", foreign key (drink_id) references drink(_id) on delete no action on update no action)";


    public RestaurantDatabaseHelper(@Nullable Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL(CREATE_FOOD_TABLE);
        sqLiteDatabase.execSQL(CREATE_DRINK_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL(CREATE_ORDERS_TABLE);
    }

    @Override
    public void onDowngrade(SQLiteDatabase sqLiteDatabase, int oldVersion, int newVersion) {
    }

    private void insertFood(SQLiteDatabase db, String name, String description, Integer weight
            , int resourceId) {

        final ContentValues values = new ContentValues();

        values.put("name", name);
        values.put("description", description);
        values.put("weight", weight);
        values.put("resource", resourceId);

        db.insert("food", null, values);
    }

    private void insertDrink(SQLiteDatabase db, String name, String description, Integer volume
            , int resourceId) {

        final ContentValues values = new ContentValues();

        values.put("name", name);
        values.put("description", description);
        values.put("volume", volume);
        values.put("resource", resourceId);

        db.insert("drink", null, values);
    }

    private void inflateFood(SQLiteDatabase sqLiteDatabase) {
        insertFood(sqLiteDatabase, "Dumplings"
                , "Delicious dumplings with beef and sour cream", 400, R.drawable.pelmeni);
        insertFood(sqLiteDatabase, "Caesar salad"
                , "Diet salad with chicken and vegetables", 300, R.drawable.caesar);
        insertFood(sqLiteDatabase, "Borsch"
                , "Delicious borsch with potato, beef, beet and sour cream", 450, R.drawable.borsch);

        insertFood(sqLiteDatabase, "Roast Potato"
                , "Whole fried young potatoes with herbs", 300, R.drawable.potato);
        insertFood(sqLiteDatabase, "Hamburger"
                , "Delicious burger with beef and cheese", 250, R.drawable.hamburger);

    }

}
