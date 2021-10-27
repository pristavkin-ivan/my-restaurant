package com.vano.myrestaurant.model.view

import android.content.Context
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.core.view.setMargins
import com.vano.myrestaurant.R

class RestaurantCardView private constructor(context: Context): CardView(context) {

    companion object {

        private const val CARD_HEIGHT = 550
        private const val ELEVATION = 4f
        private const val RADIUS = 6f
        private const val ZERO = 0
        private const val IMAGE_WEIGHT = 1f
        private const val MARGIN = 8

        fun getInstance(context: Context) = RestaurantCardView(context).apply {
            layoutParams = getMarginLayoutParams()
            elevation = ELEVATION
            radius = RADIUS
            setCardBackgroundColor(context.getColor(R.color.card_background))

            addView(getLinearLayout(context))
        }
    }

    private fun getMarginLayoutParams(): MarginLayoutParams {
        val layoutParams = MarginLayoutParams(
            ViewGroup.LayoutParams.MATCH_PARENT, CARD_HEIGHT
        )
        layoutParams.setMargins(MARGIN)
        return layoutParams
    }

    private fun getLinearLayout(context: Context): LinearLayout {
        val linearLayout = LinearLayout(context)

        linearLayout.layoutParams = LinearLayout.LayoutParams(
            LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT
        )
        linearLayout.orientation = LinearLayout.VERTICAL
        linearLayout.addView(getImageView(context))
        linearLayout.addView(getTextView(context))

        return linearLayout
    }

    private fun getTextView(context: Context): TextView {
        val textView = TextView(context)

        textView.layoutParams = LinearLayout.LayoutParams(
            LinearLayout.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT
        )
        textView.id = R.id.name
        return textView
    }

    private fun getImageView(context: Context): ImageView {
        val imageView = ImageView(context)

        imageView.layoutParams = LinearLayout.LayoutParams(
            LinearLayout.LayoutParams.MATCH_PARENT,
            ZERO,
            IMAGE_WEIGHT
        )
        imageView.id = R.id.photo
        return imageView
    }
}