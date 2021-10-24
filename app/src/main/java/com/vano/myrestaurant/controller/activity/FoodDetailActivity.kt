package com.vano.myrestaurant.controller.activity

import android.animation.AnimatorSet
import android.animation.ObjectAnimator
import android.annotation.SuppressLint
import android.app.*
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.view.animation.LinearInterpolator
import android.widget.CheckBox
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.NotificationCompat
import com.vano.myrestaurant.R
import com.vano.myrestaurant.databinding.ActivityFoodDetail1Binding
import com.vano.myrestaurant.model.entity.Food
import com.vano.myrestaurant.model.service.FoodService
import com.vano.myrestaurant.model.util.ActivityUtil.configureActionBar

class FoodDetailActivity : AppCompatActivity() {

    private companion object {
        const val ID = "id"
        const val ZERO_VALUE = 0
        const val NOTIFICATION_ID_10 = 10
        const val NOTIFICATION_ID_11 = 11
        const val CHANNEL_ID = "5"
        const val DURATION_1000 = 1000L
        const val DURATION_600 = 600L
        const val DELTA_Y = 1900
        const val CHANNEL_NAME = "chan"
    }

    private var foodService: FoodService? = null

    private var binding: ActivityFoodDetail1Binding? = null

    private val foodId: Int
        get() {
            val intent = intent
            return intent.getIntExtra(ID, ZERO_VALUE)
        }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityFoodDetail1Binding.inflate(layoutInflater)
        setContentView(binding?.root)

        foodService = FoodService(this)

        configureActionBar(this, getString(R.string.food_title))

        foodService?.read(foodId)?.let { setFoodInfo(it) }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.favorite -> {
                val intent = Intent(this, FavoriteFoodActivity::class.java)
                startActivity(intent)
            }
            R.id.create_order -> {
                val intent = Intent(this, OrderActivity::class.java)
                startActivity(intent)
            }
        }
        return super.onOptionsItemSelected(item)
    }

    @SuppressLint("SetTextI18n")
    private fun setFoodInfo(food: Food) {
        binding?. let {
            val photo = findViewById<ImageView>(R.id.photo)
            val name = findViewById<TextView>(R.id.name)
            val description = findViewById<TextView>(R.id.description)
            val weight = findViewById<TextView>(R.id.weight)
            val favorite = findViewById<CheckBox>(R.id.favorite)

            photo.setImageResource(food.resourceId)
            photo.contentDescription = food.name
            name.text = food.name
            description.text = food.description
            weight.text = food.weight.toString() + getString(R.string.weight_measure_unit)
            favorite.isChecked = food.favorite

            favorite.setOnClickListener(this::onFavoriteClicked)
        }
    }

    private fun onFavoriteClicked(checkBox: View) {
        val checkBox1 = checkBox as CheckBox
        val notificationManger = getSystemService(NOTIFICATION_SERVICE) as NotificationManager

        createNotificationChannel(notificationManger)
        handleUserClick(checkBox1, notificationManger, foodService?.read(foodId))
    }

    private fun handleUserClick(
        checkBox1: CheckBox,
        notificationManger: NotificationManager,
        food: Food? = null
    ) {
        if (checkBox1.isChecked) {
            foodService?.addToFavorite(foodId)
            setAnimation(food?.resourceId ?: R.drawable.ic_done_white_24dp)
            notificationManger
                .notify(NOTIFICATION_ID_10, buildNotification(getString(R.string.add_to_favorites)))
        } else {
            foodService?.deleteFromFavorite(foodId)
            notificationManger
                .notify(NOTIFICATION_ID_11, buildNotification(getString(R.string.delete_from_favorites)))
        }
    }

    private fun setAnimation(resourceId: Int) = binding?.image?.let {
        it.setImageResource(resourceId)

        val animatorSet = AnimatorSet()

        animatorSet.duration = DURATION_600
        animatorSet.interpolator = LinearInterpolator()

        animatorSet.playTogether(it.yAnimator, it.alfaAnimator)
        animatorSet.start()
    }

    private val View.yAnimator: ObjectAnimator
        get() = ObjectAnimator.ofFloat(
            this,
            View.TRANSLATION_Y,
            translationY,
            translationY - DELTA_Y
        )

    private val View.alfaAnimator: ObjectAnimator
        get() = ObjectAnimator.ofFloat(
            this,
            View.ALPHA,
            alpha,
            ZERO_VALUE.toFloat()
        )

    private fun createNotificationChannel(notificationManger: NotificationManager) {
        val notificationChannel: NotificationChannel
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            notificationChannel = NotificationChannel(
                CHANNEL_ID,
                CHANNEL_NAME,
                NotificationManager.IMPORTANCE_DEFAULT
            )
            notificationManger.createNotificationChannel(notificationChannel)
        }
    }

    private fun buildNotification(text: String): Notification {
        val builder: NotificationCompat.Builder =
            NotificationCompat.Builder(this, CHANNEL_ID)
        builder.setContentTitle(getString(R.string.notification))
            .setSmallIcon(android.R.drawable.ic_dialog_info)
            .setContentText(text)
            .setPriority(NotificationCompat.PRIORITY_HIGH)
            .setVibrate(longArrayOf(ZERO_VALUE.toLong(), DURATION_1000))
            .setAutoCancel(true)
            .setContentIntent(createPendingIntent())
        return builder.build()
    }

    @SuppressLint("UnspecifiedImmutableFlag")
    private fun createPendingIntent(): PendingIntent? {
        val intent = Intent(this, FavoriteFoodActivity::class.java)
        return PendingIntent.getActivity(
            this, ZERO_VALUE, intent, PendingIntent.FLAG_UPDATE_CURRENT
        )
    }
}