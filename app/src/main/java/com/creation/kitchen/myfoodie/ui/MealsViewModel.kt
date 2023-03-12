package com.creation.kitchen.myfoodie.ui

import androidx.lifecycle.*
import androidx.lifecycle.viewmodel.CreationExtras
import com.creation.kitchen.myfoodie.database.asMealList
import com.creation.kitchen.myfoodie.database.getDatabase
import com.creation.kitchen.myfoodie.network.MyFoodieApi
import com.creation.kitchen.myfoodie.repository.MealRepository
import com.creation.kitchen.myfoodie.ui.model.Meal
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import retrofit2.HttpException
import java.io.IOException

sealed interface MealsUiState {
    data class Success(val meals: List<Meal>) : MealsUiState

    data class SuccessSaved(val meals: Flow<List<Meal>>): MealsUiState

    object Error : MealsUiState

    object Loading : MealsUiState
}

class MealsViewModel(
    private val mealRepository: MealRepository,
    savedStateHandle: SavedStateHandle,
) : ViewModel() {

    private val category = savedStateHandle.get<String>("category")
    private val _mealsUiState = MutableStateFlow<MealsUiState>(MealsUiState.Loading)
    val mealsUiState: StateFlow<MealsUiState> = _mealsUiState

    init {
        getMeals()
    }

    fun getMeals() {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                category?.let {
                    if (category == "saved") {
                        val listResult = mealRepository.getSavedMeals().map { dbMeals ->
                            dbMeals.asMealList()
                        }
                        _mealsUiState.value = MealsUiState.SuccessSaved(listResult)
                    } else {
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

    companion object {

        val Factory: ViewModelProvider.Factory = object : ViewModelProvider.Factory {
            @Suppress("UNCHECKED_CAST")
            override fun <T : ViewModel> create(
                modelClass: Class<T>,
                extras: CreationExtras
            ): T {
                // Get the Application object from extras
                val application = checkNotNull(extras[ViewModelProvider.AndroidViewModelFactory.APPLICATION_KEY])
                // Create a SavedStateHandle for this ViewModel from extras
                val savedStateHandle = extras.createSavedStateHandle()

                val repository = MealRepository(getDatabase(application))

                return MealsViewModel(
                    repository,
                    savedStateHandle
                ) as T
            }
        }
    }
}