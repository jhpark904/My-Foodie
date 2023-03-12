package com.creation.kitchen.myfoodie.database

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class DatabaseMeal(
    @PrimaryKey
    val id: String,
    val name: String,
    val covertArtUrl: String,
)