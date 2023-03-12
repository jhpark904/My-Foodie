package com.creation.kitchen.myfoodie.ui

import androidx.lifecycle.*
import androidx.lifecycle.ViewModelProvider.AndroidViewModelFactory.Companion.APPLICATION_KEY
import androidx.lifecycle.viewmodel.CreationExtras
import com.creation.kitchen.myfoodie.database.DatabaseMeal
import com.creation.kitchen.myfoodie.database.getDatabase
import com.creation.kitchen.myfoodie.network.MyFoodieApi
import com.creation.kitchen.myfoodie.repository.MealRepository
import com.creation.kitchen.myfoodie.ui.model.MealDetails
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import retrofit2.HttpException
import java.io.IOException

sealed interface MealDetailsUiState {

    data class Success(val mealDetails: MealDetails) : MealDetailsUiState

    object Error : MealDetailsUiState

    object Loading : MealDetailsUiState
}

class MealDetailsViewModel(
    private val mealRepository: MealRepository,
    savedStateHandle: SavedStateHandle
) : ViewModel() {

    private val mealId = savedStateHandle.get<String>("mealId")
    private val _mealDetailsUiState =
        MutableStateFlow<MealDetailsUiState>(MealDetailsUiState.Loading)
    val mealDetailsUiState: StateFlow<MealDetailsUiState> = _mealDetailsUiState
    val isSaved = mealRepository.isSaved(mealId!!)

    private val _showRemoveToast = MutableStateFlow(false)
    val showRemoveToast: StateFlow<Boolean> = _showRemoveToast

    private val _showSaveToast = MutableStateFlow(false)
    val showSaveToast: StateFlow<Boolean> = _showSaveToast

    init {
        getMealDetails()
    }

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

    fun saveMeal(meal: DatabaseMeal) {
        viewModelScope.launch(Dispatchers.IO) {
            mealRepository.saveMeal(meal)
            _showSaveToast.value = true
        }
    }

    fun removeMeal(meal: DatabaseMeal) {
        viewModelScope.launch(Dispatchers.IO) {
            mealRepository.removeMeal(meal)
            _showRemoveToast.value = true
        }
    }

    fun alterRemoveToast() {
        _showRemoveToast.value = false
    }

    fun alterSaveToast() {
        _showSaveToast.value = false
    }

    companion object {

        val Factory: ViewModelProvider.Factory = object : ViewModelProvider.Factory {
            @Suppress("UNCHECKED_CAST")
            override fun <T : ViewModel> create(
                modelClass: Class<T>,
                extras: CreationExtras
            ): T {
                // Get the Application object from extras
                val application = checkNotNull(extras[APPLICATION_KEY])
                // Create a SavedStateHandle for this ViewModel from extras
                val savedStateHandle = extras.createSavedStateHandle()

                val repository = MealRepository(getDatabase(application))

                return MealDetailsViewModel(
                    repository,
                    savedStateHandle
                ) as T
            }
        }
    }
}