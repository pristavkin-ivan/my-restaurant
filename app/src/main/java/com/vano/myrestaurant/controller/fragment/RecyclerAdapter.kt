package com.vano.myrestaurant.controller.fragment

import android.app.ActivityOptions
import android.os.Bundle
import androidx.recyclerview.widget.RecyclerView
import com.vano.myrestaurant.controller.fragment.RecyclerAdapter.CardViewHolder
import android.view.ViewGroup
import android.view.LayoutInflater
import android.widget.ImageView
import androidx.cardview.widget.CardView
import com.vano.myrestaurant.R
import android.widget.TextView
import androidx.fragment.app.Fragment

class RecyclerAdapter(
    private val photos: List<Int>,
    private val captions: List<String>,
    private val listener: Listener?
) : RecyclerView.Adapter<CardViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CardViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val cardView = layoutInflater.inflate(
            R.layout.card, parent, false
        ) as CardView

        return CardViewHolder(cardView)
    }

    override fun onBindViewHolder(holder: CardViewHolder, position: Int) {
        val cardView = holder.cardView
        val name = cardView.findViewById<TextView>(R.id.name)
        val image = cardView.findViewById<ImageView>(R.id.photo)

        name.text = captions[position]
        image.setImageResource(photos[position])
        image.contentDescription = captions[position]

        if (listener != null) {
            cardView.setOnClickListener {
                val options = ActivityOptions.makeSceneTransitionAnimation(
                    (listener as Fragment).activity, holder.cardView, listener.getString(R.string.transition_name)
                )
                listener.onItemClick(position, options.toBundle())
            }
        }
    }

    override fun getItemCount() =  photos.size

    class CardViewHolder(var cardView: CardView) : RecyclerView.ViewHolder(cardView)

    interface Listener {
        fun onItemClick(position: Int, bundle: Bundle?)
    }

}