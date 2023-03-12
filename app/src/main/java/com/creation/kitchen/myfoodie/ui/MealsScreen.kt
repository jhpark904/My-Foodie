package com.creation.kitchen.myfoodie.ui

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.dimensionResource
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

        is MealsUiState.SuccessSaved -> {
           LoadedResult(
               category = category,
               meals = state.meals.collectAsState(initial = emptyList()).value,
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
    Column(
        modifier = Modifier
            .padding(dimensionResource(id = R.dimen.d5))
            .fillMaxSize()
    ) {
        category?.let {
            if (it != stringResource(id = R.string.saved)) {
                Text(
                    stringResource(id = R.string.category, it),
                    style = MaterialTheme.typography.h5,
                    color = MaterialTheme.colors.primary
                )
            } else {
                Text(
                    stringResource(id = R.string.saved_meals),
                    style = MaterialTheme.typography.h5,
                    color = MaterialTheme.colors.primary
                )
            }
        }

        Spacer(modifier = Modifier.height(dimensionResource(id = R.dimen.d3)))

        LazyColumn {
            items(meals) { meal ->
                MealRow(
                    coverArtUrl = meal.coverArtUrl,
                    name = meal.name,
                    id = meal.id,
                    navigateToMealDetails = navigateToMealDetails
                )
            }
        }
    }
}

@Composable
fun MealRow(
    coverArtUrl: String,
    name: String,
    id: String,
    navigateToMealDetails: (String) -> Unit
) {
    Surface(
        shape = MaterialTheme.shapes.medium,
        elevation = dimensionResource(id = R.dimen.elevation)
    ) {
        Row(
            modifier = Modifier
                .padding(
                    dimensionResource(id = R.dimen.d2)
                )
                .fillMaxWidth()
                .clickable {
                    navigateToMealDetails(id)
                }
        ) {
            AsyncImage(
                model = coverArtUrl,
                null,
                modifier = Modifier
                    .size(dimensionResource(id = R.dimen.d15))
                    .clip(CircleShape)
            )

            Spacer(modifier = Modifier.width(dimensionResource(id = R.dimen.d1)))

            Text(
                name,
                style = MaterialTheme.typography.body1,
                color = MaterialTheme.colors.primary
            )
        }
    }
}