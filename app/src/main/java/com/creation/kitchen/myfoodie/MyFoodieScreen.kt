package com.creation.kitchen.myfoodie

import androidx.compose.foundation.layout.padding
import androidx.compose.material.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.creation.kitchen.myfoodie.ui.DiscoverScreen
import com.creation.kitchen.myfoodie.ui.HomeScreen
import com.creation.kitchen.myfoodie.ui.SearchScree

enum class MyFoodieScreen {
    Home,
    Discover,
    Search,
    Meals
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
                DiscoverScreen(
                    navigateToMeals = {
                        navController.navigate(MyFoodieScreen.Meals.name)
                    }
                )
            }

            composable(route = MyFoodieScreen.Search.name) {
                SearchScree()
            }

            composable(route = MyFoodieScreen.Meals.name) {

            }
        }
    }
}