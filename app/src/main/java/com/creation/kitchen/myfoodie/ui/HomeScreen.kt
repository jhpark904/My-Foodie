package com.creation.kitchen.myfoodie.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material.Button
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import com.creation.kitchen.myfoodie.R

@Composable
fun HomeScreen(
    onDiscoverClick: () -> Unit,
    onSearchClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = modifier
            .padding(dimensionResource(id = R.dimen.d1))
            .fillMaxSize()
    ) {
        Spacer(modifier = Modifier.height(dimensionResource(id = R.dimen.d3)))

        Image(
            painter = painterResource(id = R.drawable.ic_launcher_foreground),
            contentDescription = stringResource(id = R.string.image_of_chicken),
        )
        
        Spacer(modifier = Modifier.height(dimensionResource(id = R.dimen.d1)))
        
        Button(
            onClick = onDiscoverClick,
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(
                text = stringResource(id = R.string.discover_foods),
                style = MaterialTheme.typography.button
            )
        }
        
        Button(
            onClick = onSearchClick,
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(
                text = stringResource(id = R.string.search_foods),
                style = MaterialTheme.typography.button
            )
        }
    }
}

@Preview
@Composable
fun previewHomeScreen() {
    HomeScreen(onDiscoverClick = {}, {})
}