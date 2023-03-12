package com.creation.kitchen.myfoodie.database

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.creation.kitchen.myfoodie.ui.model.Meal

@Entity
data class DatabaseMeal(
    @PrimaryKey
    val id: String,
    val name: String,
    val covertArtUrl: String,
)

fun List<DatabaseMeal>.asMealList(): List<Meal> {
    return map {
        Meal(
            it.name,
            it.covertArtUrl,
            it.id
        )
    }
}