package com.creation.kitchen.myfoodie.ui

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import coil.compose.AsyncImage
import com.creation.kitchen.myfoodie.R
import com.creation.kitchen.myfoodie.ui.components.ErrorScreen
import com.creation.kitchen.myfoodie.ui.components.LoadingScreen

@Composable
fun MealDetailsScreen(
    viewModel: MealDetailsViewModel,
    reloadAction: () -> Unit
) {
    when (val state = viewModel.mealDetailsUiState.collectAsState().value) {
        is MealDetailsUiState.Success -> {
            state.mealDetails.let {
                LoadedResult(
                    coverArtUrl = it.coverArtUrl,
                    name = it.name,
                    area = it.area,
                    instruction = it.instruction
                )
            }
        }

        is MealDetailsUiState.Loading -> {
           LoadingScreen()
        }

        is MealDetailsUiState.Error -> {
            ErrorScreen(retryAction = reloadAction)
        }
    }
}

@Composable
fun LoadedResult(
    coverArtUrl: String,
    name: String,
    area: String,
    instruction: String
) {
    Column(
        modifier = Modifier
            .padding(dimensionResource(id = R.dimen.d3))
            .fillMaxSize()
            .verticalScroll(rememberScrollState()),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        AsyncImage(
            model = coverArtUrl,
            contentDescription = null,
            modifier = Modifier.clip(CircleShape)
        )

        Spacer(modifier = Modifier.height(dimensionResource(id =R.dimen.d3)))

        Text(
            name,
            style = MaterialTheme.typography.h5,
            color = MaterialTheme.colors.primary
        )

        Text(
            area,
            style = MaterialTheme.typography.subtitle2,
            color = MaterialTheme.colors.primaryVariant
        )

        Spacer(modifier = Modifier.height(dimensionResource(id =R.dimen.d3)))

        Text(
            text = stringResource(id = R.string.instructions),
            style = MaterialTheme.typography.subtitle1,
            color = MaterialTheme.colors.primary
        )

        Text(
            instruction,
            style = MaterialTheme.typography.subtitle2,
            color = MaterialTheme.colors.primaryVariant
        )
    }
}