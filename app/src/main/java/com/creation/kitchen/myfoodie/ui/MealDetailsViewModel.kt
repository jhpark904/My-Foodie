package com.creation.kitchen.myfoodie.ui

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.creation.kitchen.myfoodie.network.MyFoodieApi
import com.creation.kitchen.myfoodie.ui.model.MealDetails
import com.creation.kitchen.myfoodie.ui.model.MealDetailsResponse
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.json.Json
import retrofit2.HttpException
import java.io.IOException

sealed interface MealDetailsUiState {

    data class Success(val mealDetails: MealDetails) : MealDetailsUiState

    object Error : MealDetailsUiState

    object Loading : MealDetailsUiState
}

class MealDetailsViewModel(savedStateHandle: SavedStateHandle) :
    ViewModel() {

    private val mealId = savedStateHandle.get<String>("mealId")
    private val _mealDetailsUiState =
        MutableStateFlow<MealDetailsUiState>(MealDetailsUiState.Loading)
    val mealDetailsUiState = _mealDetailsUiState

    init {
        getMealDetails()
    }


    private val json = Json { ignoreUnknownKeys = true }

    fun getMealDetails() {
        viewModelScope.launch {
            try {
                mealId?.let {
                    val listResult = MyFoodieApi.retrofitService.getMealDetails(it).mealDetails
                    _mealDetailsUiState.value =
                        MealDetailsUiState.Success(mealDetails = listResult[0])
                }
            } catch (e: IOException) {
                _mealDetailsUiState.value = MealDetailsUiState.Error
            } catch (e: HttpException) {
                _mealDetailsUiState.value = MealDetailsUiState.Error
            }
        }
    }
}