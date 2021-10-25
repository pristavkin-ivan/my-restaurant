package com.vano.myrestaurant.adapter

import android.annotation.SuppressLint
import android.app.ActivityOptions
import android.os.Bundle
import androidx.recyclerview.widget.RecyclerView
import com.vano.myrestaurant.adapter.RecyclerAdapter.CardViewHolder
import android.view.ViewGroup
import android.view.LayoutInflater
import android.widget.ImageView
import androidx.cardview.widget.CardView
import com.vano.myrestaurant.R
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.vano.myrestaurant.entity.Card
import java.util.*

class RecyclerAdapter : RecyclerView.Adapter<CardViewHolder>() {

    var cards: List<Card> = Collections.emptyList()
        @SuppressLint("NotifyDataSetChanged")
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    var listener: Listener? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CardViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val cardView = layoutInflater.inflate(
            R.layout.card, parent, false
        ) as CardView

        return CardViewHolder(cardView)
    }

    override fun onBindViewHolder(holder: CardViewHolder, position: Int) {
        holder.fillView(position, cards, listener)
    }

    override fun getItemCount() = cards.size

    class CardViewHolder(private var cardView: CardView) : RecyclerView.ViewHolder(cardView) {

        fun fillView(position: Int, cards: List<Card>, listener: Listener?) {
            val name = cardView.findViewById<TextView>(R.id.name)
            val image = cardView.findViewById<ImageView>(R.id.photo)

            name.text = cards[position].caption
            image.setImageResource(cards[position].resourceId)
            image.contentDescription = cards[position].caption

            if (listener != null) {
                cardView.setOnClickListener {
                    val options = ActivityOptions.makeSceneTransitionAnimation(
                        (listener as Fragment).activity,
                        cardView,
                        (listener as Fragment).getString(R.string.transition_name)
                    )
                    listener.onItemClick(position, options.toBundle())
                }
            }
        }
    }

    interface Listener {
        fun onItemClick(position: Int, bundle: Bundle?)
    }

}