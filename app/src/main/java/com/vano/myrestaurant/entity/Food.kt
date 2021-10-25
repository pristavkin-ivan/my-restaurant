package com.vano.myrestaurant.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity
data class Food(
    @PrimaryKey(autoGenerate = true)
    var id: Int,
    var name: String,
    var description: String,
    var weight: Int,
    var resourceId: Int,
    @ColumnInfo(defaultValue = "false")
    var favorite: Boolean = false
) {
    override fun toString() = name
}