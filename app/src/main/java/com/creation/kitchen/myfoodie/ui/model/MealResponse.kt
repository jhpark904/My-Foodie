package com.creation.kitchen.myfoodie.ui.model

import kotlinx.serialization.SerialInfo
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Meal(
    @SerialName(value = "strMeal")
    val name: String,
    @SerialName(value = "strMealThumb")
    val coverArtUrl: String,
    @SerialName(value = "idMeal")
    val id: String
)

@Serializable
data class MealResponse(
    @SerialName(value = "meals")
    val meals: List<Meal>
)
