package com.creation.kitchen.myfoodie.ui.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Category(
    @SerialName(value = "strCategory")
    val name: String
)

@Serializable
data class CategoryResponse(
    @SerialName(value = "meals")
    val categories: List<Category>
)