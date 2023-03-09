package com.creation.kitchen.myfoodie.ui

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material.Button
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import com.creation.kitchen.myfoodie.R
import com.creation.kitchen.myfoodie.ui.components.ErrorScreen
import com.creation.kitchen.myfoodie.ui.components.LoadingScreen
import com.creation.kitchen.myfoodie.ui.model.Category

@Composable
fun DiscoverScreen(
    viewModel: DiscoverViewModel,
    navigateToMeals: (String) -> Unit,
    reloadAction: () -> Unit
) {
    when (val state = viewModel.discoverUiState.collectAsState().value) {
        is DiscoverUiState.Success -> {
            LoadedResult(
                filters = state.filters,
                navigateToMeals = navigateToMeals
            )
        }

        is DiscoverUiState.Error -> {
            ErrorScreen(retryAction = reloadAction)
        }

        is DiscoverUiState.Loading -> {
            LoadingScreen()
        }
    }
}

@Composable
fun LoadedResult(
    filters: List<Category>,
    navigateToMeals: (String) -> Unit
) {
    Column(
        Modifier
            .fillMaxSize()
            .padding(dimensionResource(id = R.dimen.d3))
    ) {
        Text(
            stringResource(id = R.string.categories),
            style = MaterialTheme.typography.h5,
            color = MaterialTheme.colors.primary
        )

        Spacer(modifier = Modifier.height(dimensionResource(id = R.dimen.d3)))

        LazyVerticalGrid(
            columns = GridCells.Fixed(2),
            horizontalArrangement = Arrangement.spacedBy(dimensionResource(id = R.dimen.d1))
        ) {
            items(filters) { category ->
                CategoryButton(
                    category = category.name,
                    navigateToMeals = navigateToMeals
                )
            }
        }
    }
}

@Composable
fun CategoryButton(
    category: String,
    navigateToMeals: (String) -> Unit
) {
    Button(onClick = { navigateToMeals(category) }) {
        Text(
            text = category,
            style = MaterialTheme.typography.button,
        )
    }
}



