package com.creation.kitchen.myfoodie

import androidx.annotation.StringRes
import androidx.compose.foundation.layout.padding
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.creation.kitchen.myfoodie.ui.*

enum class MyFoodieScreen(@StringRes val title: Int) {
    Home(R.string.app_name),
    Discover(R.string.discover_foods),
    Meals(R.string.meals),
    MealDetails(R.string.meal_details)
}

@Composable
fun MyFoodieAppTopBar(
    currentScreen: MyFoodieScreen,
    navigateUp: () -> Unit,
    canNavigateBack: Boolean
) {
    TopAppBar(
        title = { Text(stringResource(id = currentScreen.title)) },
        navigationIcon = {
            if (canNavigateBack) {
                IconButton(onClick = navigateUp) {
                    Icon(
                        imageVector = Icons.Filled.ArrowBack,
                        contentDescription = stringResource(id = R.string.back)
                    )
                }
            }
        }
    )
}

@Composable
fun MyFoodieApp(
    navController: NavHostController = rememberNavController()
) {
    // Get current back stack entry
    val backStackEntry by navController.currentBackStackEntryAsState()
    val route = backStackEntry?.destination?.route
        ?.replaceAfter("/", "")
        ?.removeSuffix("/")

    // Get the name of the current screen
    val currentScreen = MyFoodieScreen.valueOf(
        route ?: MyFoodieScreen.Home.name
    )

    Scaffold(
        topBar = {
            MyFoodieAppTopBar(
                currentScreen = currentScreen,
                canNavigateBack = navController.previousBackStackEntry != null,
                navigateUp = { navController.navigateUp() }
            )
        }
    ) { innerPadding ->
        NavHost(
            navController = navController,
            startDestination = MyFoodieScreen.Home.name,
            modifier = Modifier.padding(innerPadding),
        ) {
            composable(route = MyFoodieScreen.Home.name) {
                val category = stringResource(id = R.string.saved)
                HomeScreen(
                    onDiscoverClick = {
                        navController.navigate(MyFoodieScreen.Discover.name)
                    },
                    onSearchClick = {
                        navController.navigate(
                            "${MyFoodieScreen.Meals.name}/${category}"
                        )
                    }
                )
            }

            composable(route = MyFoodieScreen.Discover.name) {
                val discoverViewModel: DiscoverViewModel = viewModel()

                DiscoverScreen(
                    viewModel = discoverViewModel,
                    navigateToMeals = { category: String ->
                        navController.navigate("${MyFoodieScreen.Meals.name}/${category}")
                    },
                    reloadAction = discoverViewModel::getCategories
                )
            }

            composable(route = "${MyFoodieScreen.Meals.name}/{category}") {
                val category = it.arguments?.getString("category")
                val mealsViewModel: MealsViewModel = viewModel(factory = MealsViewModel.Factory)

                MealsScreen(
                    viewModel = mealsViewModel,
                    category = category,
                    navigateToMealDetails = { mealId: String ->
                        navController.navigate("${MyFoodieScreen.MealDetails.name}/${mealId}")
                    },
                    reloadAction = mealsViewModel::getMeals
                )
            }

            composable(route = "${MyFoodieScreen.MealDetails.name}/{mealId}") {
                val mealDetailsViewModel: MealDetailsViewModel =
                    viewModel(factory = MealDetailsViewModel.Factory)

                MealDetailsScreen(
                    viewModel = mealDetailsViewModel,
                    reloadAction = mealDetailsViewModel::getMealDetails
                )
            }
        }
    }
}