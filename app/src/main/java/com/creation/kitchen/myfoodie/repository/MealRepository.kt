package com.creation.kitchen.myfoodie.repository

import com.creation.kitchen.myfoodie.database.DatabaseMeal
import com.creation.kitchen.myfoodie.database.MealDatabase


class MealRepository(
    private val database: MealDatabase
) {
    suspend fun saveMeal(meal: DatabaseMeal) {
        database.mealDao().insert(meal)
    }

    suspend fun removeMeal(meal: DatabaseMeal) {
        database.mealDao().delete(meal)
    }

    fun isSaved(mealId: String) = database.mealDao().isSaved(mealId)

    fun getSavedMeals() = database.mealDao().getMeals()
}