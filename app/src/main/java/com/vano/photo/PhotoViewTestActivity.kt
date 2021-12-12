package com.vano.photo

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.github.chrisbanes.photoview.PhotoView
import com.vano.myrestaurant.R

class PhotoViewTestActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_photo_view_test)

        val photoView = findViewById<PhotoView>(R.id.photo_view)
        photoView.setImageResource(R.drawable.green_tea)

        photoView.alpha = 0.8f
        photoView.maximumScale = 5f
    }
}