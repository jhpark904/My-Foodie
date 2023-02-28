package com.creation.kitchen.myfoodie.ui

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
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
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.creation.kitchen.myfoodie.R
import com.creation.kitchen.myfoodie.ui.model.Category

@Composable
fun DiscoverScreen(
    viewModel: DiscoverViewModel = viewModel(),
    navigateToMeals: () -> Unit,
) {
    when(val state = viewModel.discoverUiState.collectAsState().value) {
        is DiscoverUiState.Success -> {
            LoadedResult(
                filters = state.filters,
                navigateToMeals = navigateToMeals
            )
        }

        is DiscoverUiState.Error -> {
            Text("Error")
        }

        is DiscoverUiState.Loading -> {
            Text("Loading...")
        }
    }
}

@Composable
fun LoadedResult(
    filters: List<Category>,
    navigateToMeals: () -> Unit
) {
    Column(
        Modifier
            .fillMaxSize()
            .padding(dimensionResource(id = R.dimen.d3))
    ) {
        Text(
            stringResource(id = R.string.categories),
            style = MaterialTheme.typography.subtitle1
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
    navigateToMeals: () -> Unit
) {
    Button(onClick = navigateToMeals) {
        Text(
            text = category,
            style = MaterialTheme.typography.button
        )
    }
}



