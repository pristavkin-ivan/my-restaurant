package com.vano.myweather.model.adapter

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.vano.myrestaurant.R
import com.vano.myweather.model.entity.City

class CityAdapter(val context: Context): RecyclerView.Adapter<CityAdapter.ViewHolder>() {

    var cities: List<City>? = null
        @SuppressLint("NotifyDataSetChanged")
        set(value) {
            field = value
            notifyDataSetChanged()
        }
    var listener: Listener? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.row, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.city = cities?.get(position)
        holder.fillView()
        holder.view.setOnClickListener {
            listener?.onClick(holder.city)
        }
    }

    override fun getItemCount() = cities?.size ?: 0

    class ViewHolder(val view: View): RecyclerView.ViewHolder(view) {

        var city: City? = null

        fun fillView() {
            view.findViewById<TextView>(R.id.name).text = city?.name
        }
    }

    interface Listener {
        fun onClick(city: City?)
    }
}