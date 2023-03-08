package com.creation.kitchen.myfoodie.ui

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.creation.kitchen.myfoodie.network.MyFoodieApi
import com.creation.kitchen.myfoodie.ui.model.Meal
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import retrofit2.HttpException
import java.io.IOException

sealed interface MealsUiState {
    data class Success(val meals: List<Meal>): MealsUiState

    object Error: MealsUiState

    object Loading: MealsUiState
}

class MealsViewModel(savedStateHandle: SavedStateHandle): ViewModel() {

    private val category = savedStateHandle.get<String>("category")
    private val _mealsUiState = MutableStateFlow<MealsUiState>(MealsUiState.Loading)
    val mealsUiState: StateFlow<MealsUiState> = _mealsUiState

    init {
        getMeals()
    }

     fun getMeals() {
        try {
            viewModelScope.launch {
                category?.let {
                    val listResult = MyFoodieApi.retrofitService.getMealsList(it).meals
                    _mealsUiState.value = MealsUiState.Success(listResult)
                }
            }
        } catch (e: IOException) {
            _mealsUiState.value = MealsUiState.Error
        } catch (e: HttpException) {
            _mealsUiState.value = MealsUiState.Error
        }
    }

}