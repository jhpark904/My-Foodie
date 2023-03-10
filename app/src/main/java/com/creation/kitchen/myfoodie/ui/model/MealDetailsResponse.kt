package com.creation.kitchen.myfoodie.ui.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable


@Serializable
data class MealDetails(
    @SerialName(value = "strMeal")
    val name: String,
    @SerialName(value = "strArea")
    val area: String,
    @SerialName(value = "strInstructions")
    val instruction: String,
    @SerialName(value = "strMealThumb")
    val coverArtUrl: String
)

@Serializable
data class MealDetailsResponse(
    @SerialName(value = "meals")
    val mealDetails: List<MealDetails>
)
