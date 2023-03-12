package com.creation.kitchen.myfoodie.ui

import android.widget.Toast
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Button
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import coil.compose.AsyncImage
import com.creation.kitchen.myfoodie.R
import com.creation.kitchen.myfoodie.database.DatabaseMeal
import com.creation.kitchen.myfoodie.ui.components.ErrorScreen
import com.creation.kitchen.myfoodie.ui.components.LoadingScreen

@Composable
fun MealDetailsScreen(
    viewModel: MealDetailsViewModel,
    reloadAction: () -> Unit
) {
    val uiState = viewModel.mealDetailsUiState.collectAsState().value
    val isSaved = viewModel.isSaved.collectAsState(false).value

    if (viewModel.showRemoveToast.collectAsState().value) {
        Toast.makeText(LocalContext.current,
            stringResource(id = R.string.remove_meal),
            Toast.LENGTH_SHORT).show()
        viewModel.alterRemoveToast()
    }

    if (viewModel.showSaveToast.collectAsState().value) {
        Toast.makeText(LocalContext.current,
            stringResource(id = R.string.save_meal),
            Toast.LENGTH_SHORT).show()
        viewModel.alterSaveToast()
    }

    when (uiState) {
        is MealDetailsUiState.Success -> {
            uiState.mealDetails.let {
                LoadedResult(
                    coverArtUrl = it.coverArtUrl,
                    name = it.name,
                    area = it.area,
                    instruction = it.instruction,
                    isSaved = isSaved,
                    onSaveClicked = {
                        viewModel.saveMeal(
                            DatabaseMeal(it.id, it.name, it.coverArtUrl)
                        )
                    },
                    onRemoveClicked = {
                        viewModel.removeMeal(
                            DatabaseMeal(it.id, it.name, it.coverArtUrl)
                        )
                    }
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
    instruction: String,
    isSaved: Boolean,
    onSaveClicked: () -> Unit,
    onRemoveClicked: () -> Unit
) {
    Column(
        modifier = Modifier
            .padding(dimensionResource(id = R.dimen.d5))
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
            style = MaterialTheme.typography.subtitle1,
            color = MaterialTheme.colors.primaryVariant
        )

        Spacer(modifier = Modifier.height(dimensionResource(id =R.dimen.d3)))

        Text(
            text = stringResource(id = R.string.instructions),
            style = MaterialTheme.typography.h6,
            color = MaterialTheme.colors.primary
        )

        Spacer(modifier = Modifier.height(dimensionResource(id =R.dimen.d2)))

        Text(
            instruction,
            style = MaterialTheme.typography.subtitle1,
            color = MaterialTheme.colors.primaryVariant
        )

        Spacer(modifier = Modifier.height(dimensionResource(id =R.dimen.d2)))

        if (isSaved) {
            Button(onClick = onRemoveClicked) {
                Text(stringResource(id = R.string.remove))
            }
        } else {
            Button(onClick = onSaveClicked) {
                Text(stringResource(id = R.string.save))
            }
        }

    }
}