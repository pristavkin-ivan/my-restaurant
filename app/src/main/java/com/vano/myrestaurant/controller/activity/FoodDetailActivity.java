package com.vano.myrestaurant.controller.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;

import android.annotation.SuppressLint;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

import com.vano.myrestaurant.R;
import com.vano.myrestaurant.model.entity.Food;
import com.vano.myrestaurant.model.service.FoodService;
import com.vano.myrestaurant.model.util.ActivityUtil;

public class FoodDetailActivity extends AppCompatActivity {

    public static final String ID = "id";

    private static final String CHANNEL_ID = "5";

    private final FoodService foodService;

    private int foodId;

    public FoodDetailActivity() {
        foodService = new FoodService(this);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_food_detail);

        final Intent intent = getIntent();
        foodId = intent.getIntExtra(ID, 0);

        ActivityUtil.configureActionBar(this, getString(R.string.food_title));

        setFoodInfo(foodService.read(foodId));
    }

    @SuppressLint("SetTextI18n")
    private void setFoodInfo(Food food) {
        ImageView photo = findViewById(R.id.photo);
        TextView name = findViewById(R.id.name);
        TextView description = findViewById(R.id.description);
        TextView weight = findViewById(R.id.weight);
        CheckBox favorite = findViewById(R.id.favorite);

        photo.setImageResource(food.getResourceId());
        photo.setContentDescription(food.getName());
        name.setText(food.getName());
        description.setText(food.getDescription());
        weight.setText(food.getWeight() + getString(R.string.weight_measure_unit));
        favorite.setChecked(food.isFavorite());

        favorite.setOnClickListener(this::onFavoriteClicked);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    //todo db update in new thread
    private void onFavoriteClicked(View checkBox) {
        CheckBox checkBox1 = (CheckBox) checkBox;
        final NotificationManager notificationManger
                = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);

        createNotificationChannel(notificationManger);
        handleUserClick(checkBox1, notificationManger);
    }

    private void handleUserClick(CheckBox checkBox1, NotificationManager notificationManger) {
        if (checkBox1.isChecked()) {
            foodService.addToFavorite(foodId);
            notificationManger
                    .notify(10, buildNotification(getString(R.string.add_to_favorites)));
        } else {
            foodService.deleteFromFavorite(foodId);
            notificationManger
                    .notify(11, buildNotification(getString(R.string.delete_from_favorites)));
        }
    }

    private void createNotificationChannel(NotificationManager notificationManger) {
        final NotificationChannel notificationChannel;
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            notificationChannel = new NotificationChannel(CHANNEL_ID, "chan", NotificationManager.IMPORTANCE_DEFAULT);
            notificationManger.createNotificationChannel(notificationChannel);
        }
    }

    private Notification buildNotification(String text) {
        NotificationCompat.Builder builder = new NotificationCompat.Builder(this, CHANNEL_ID);

        builder.setContentTitle(getString(R.string.notification))
                .setSmallIcon(android.R.drawable.ic_dialog_info).setContentText(text)
                .setPriority(NotificationCompat.PRIORITY_HIGH)
                .setVibrate(new long[]{0, 1000})
                .setAutoCancel(true)
                .setContentIntent(createPendingIntent());

        return builder.build();
    }

    @SuppressLint("UnspecifiedImmutableFlag")
    private PendingIntent createPendingIntent() {
        final Intent intent = new Intent(this, FavoriteFoodActivity.class);

        return PendingIntent.getActivity(this, 0, intent
                , PendingIntent.FLAG_UPDATE_CURRENT);
    }
}