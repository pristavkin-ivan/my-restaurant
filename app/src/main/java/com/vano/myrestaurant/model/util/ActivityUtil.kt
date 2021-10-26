package com.vano.myrestaurant.model.util

import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import com.vano.myrestaurant.R

object ActivityUtil {

    fun configureActionBar(activity: AppCompatActivity, title: String?) {
        val toolbar = activity.findViewById<Toolbar>(R.id.toolbar)
        activity.setSupportActionBar(toolbar)
        val actionBar = activity.supportActionBar

        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true)
            actionBar.title = title
        }
    }
}