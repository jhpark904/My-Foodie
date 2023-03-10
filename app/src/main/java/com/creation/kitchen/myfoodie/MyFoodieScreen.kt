package com.creation.kitchen.myfoodie

import androidx.compose.foundation.layout.padding
import androidx.compose.material.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.creation.kitchen.myfoodie.ui.*

enum class MyFoodieScreen {
    Home,
    Discover,
    Search,
    Meals,
    MealDetails
}

@Composable
fun MyFoodieApp(modifier: Modifier = Modifier) {
    val navController = rememberNavController()

    Scaffold() {innerPadding ->
        NavHost(
            navController = navController,
            startDestination = MyFoodieScreen.Home.name,
            modifier = Modifier.padding(innerPadding),
        ) {
            composable(route = MyFoodieScreen.Home.name) {
                HomeScreen(
                    onDiscoverClick = {
                        navController.navigate(MyFoodieScreen.Discover.name)
                    },
                    onSearchClick = {
                        navController.navigate(MyFoodieScreen.Search.name)
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

            composable(route = MyFoodieScreen.Search.name) {
                SearchScree()
            }

            composable(route = "${MyFoodieScreen.Meals.name}/{category}") {
                val category = it.arguments?.getString("category")
                val mealsViewModel: MealsViewModel = viewModel()

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
                val mealDetailsViewModel: MealDetailsViewModel = viewModel()
                MealDetailsScreen(
                    viewModel = mealDetailsViewModel,
                    reloadAction = mealDetailsViewModel::getMealDetails
                )
            }
        }
    }
}