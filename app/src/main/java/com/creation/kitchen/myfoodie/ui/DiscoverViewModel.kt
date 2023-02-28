package com.creation.kitchen.myfoodie.ui

import android.widget.Toast
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.creation.kitchen.myfoodie.network.MyFoodieApi
import com.creation.kitchen.myfoodie.ui.model.Category
import kotlinx.coroutines.currentCoroutineContext
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import java.io.IOException

sealed interface DiscoverUiState {

    data class Success(val filters: List<Category>): DiscoverUiState

    object Error: DiscoverUiState

    object Loading: DiscoverUiState
}

class DiscoverViewModel : ViewModel() {

    private val _discoverUiState = MutableStateFlow<DiscoverUiState>(DiscoverUiState.Loading)
    val discoverUiState: StateFlow<DiscoverUiState> = _discoverUiState

    init {
        getCategories()
    }

    private fun getCategories() {
        try {
            viewModelScope.launch {
                val listResult = MyFoodieApi.retrofitService.getCategories().categories
                _discoverUiState.value = DiscoverUiState.Success(listResult)
            }
        } catch (e: IOException) {
            _discoverUiState.value = DiscoverUiState.Error
        }
    }
}