package com.vano.myrestaurant.model.adapter

import android.annotation.SuppressLint
import android.app.Activity
import android.app.ActivityOptions
import android.os.Bundle
import androidx.recyclerview.widget.RecyclerView
import com.vano.myrestaurant.model.adapter.RecyclerAdapter.CardViewHolder
import android.view.ViewGroup
import android.widget.ImageView
import androidx.cardview.widget.CardView
import com.vano.myrestaurant.R
import android.widget.TextView
import com.vano.myrestaurant.model.entity.Card
import com.vano.myrestaurant.model.view.RestaurantCardView
import java.util.*

class RecyclerAdapter(var listener: Listener? = null) : RecyclerView.Adapter<CardViewHolder>() {

    var cards: List<Card> = Collections.emptyList()
        @SuppressLint("NotifyDataSetChanged")
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CardViewHolder {
        return CardViewHolder(RestaurantCardView.getInstance(parent.context))
    }

    override fun onBindViewHolder(holder: CardViewHolder, position: Int) {
        holder.card = cards[position]
        holder.fillView(listener)
    }

    override fun getItemCount() = cards.size

    private companion object {
        const val ZERO = 0
    }

    class CardViewHolder(private var cardView: CardView) : RecyclerView.ViewHolder(cardView) {

        var card: Card? = null

        fun fillView(listener: Listener?) {
            val name = cardView.findViewById<TextView>(R.id.name)
            val image = cardView.findViewById<ImageView>(R.id.photo)

            card?.let {
                name.text = it.caption
                image.setImageResource(it.resourceId)
                image.contentDescription = it.caption
            }

            if (listener != null) {
                cardView.setOnClickListener {
                    val options = ActivityOptions.makeSceneTransitionAnimation(
                        listener.activityContext,
                        cardView,
                        listener.activityContext?.getString(R.string.transition_name)
                    )
                    listener.onItemClick(card?.itemId ?: ZERO, options.toBundle())
                }
            }
        }
    }

    interface Listener {
        fun onItemClick(id: Int, bundle: Bundle?)
        val activityContext: Activity?
    }

}