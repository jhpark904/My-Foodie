package com.creation.kitchen.myfoodie.ui

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.res.stringResource
import coil.compose.AsyncImage
import com.creation.kitchen.myfoodie.R
import com.creation.kitchen.myfoodie.ui.components.ErrorScreen
import com.creation.kitchen.myfoodie.ui.components.LoadingScreen
import com.creation.kitchen.myfoodie.ui.model.Meal

@Composable
fun MealsScreen(
    viewModel: MealsViewModel,
    category: String?,
    navigateToMealDetails: (String) -> Unit,
    reloadAction: () -> Unit
) {
    when (val state = viewModel.mealsUiState.collectAsState().value) {
        is MealsUiState.Success -> {
            LoadedResult(
                category = category,
                meals = state.meals,
                navigateToMealDetails = navigateToMealDetails
            )
        }
        
        is MealsUiState.Loading -> {
            LoadingScreen()
        }
        
        is MealsUiState.Error -> {
            ErrorScreen(retryAction = reloadAction)
        }
    }
}

@Composable
fun LoadedResult(
    category: String?,
    meals: List<Meal>,
    navigateToMealDetails: (String) -> Unit
) {
    Column {
        category?.let {
            Text(
                stringResource(id = R.string.meals_under, it),
                style = MaterialTheme.typography.subtitle1
            )
        }

        LazyColumn {
            items(meals) { meal ->
                MealRow(
                    coverArtUrl = meal.coverArtUrl,
                    name = meal.name
                )
            }
        }
    }
}

@Composable
fun MealRow(
    coverArtUrl: String,
    name: String
) {
    Column {
       AsyncImage(
           model = coverArtUrl,
           null
       )
       Text(name)
    }
}